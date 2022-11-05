#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include "ciff.h"
#include "gif.h"
#include <deque>

std::deque<char> readCiff(const std::string& fileName){
    std::ifstream input(fileName, std::ios::binary);

    std::deque<char> bytes(
            (std::istreambuf_iterator<char>(input)),
            (std::istreambuf_iterator<char>()));
    input.close();

    bytes.erase(bytes.begin(), bytes.begin()+83);

    return bytes;
}

std::deque<char> readCaff(std::string fileName){
    std::ifstream input(fileName, std::ios::binary);

    std::deque<char> bytes(
            (std::istreambuf_iterator<char>(input)),
            (std::istreambuf_iterator<char>()));
    input.close();

    return bytes;
}

unsigned int readNumber(std::deque<char>::iterator begin, std::deque<char>::iterator end) {
    int result = 0;
    unsigned int j = 0;
    for(auto i = begin; i != end; i++, j++){
        unsigned int byte = (unsigned char)*i;
        result += byte * pow(16, 2*j);
    }

    return result;
}

std::string readText(std::deque<char>::iterator begin, std::deque<char>::iterator end) {
    std::string result;
    for(auto i = begin; i != end; i++){
        result += *i;
    }

    return result;
}

std::string readUntilNewLine(std::deque<char>::iterator iterator) {
    std::string caption;
    while(*iterator != '\n'){
        caption += *iterator;
        iterator++;
    }
    return caption;
}

std::vector<std::string> readTags(std::deque<char>::iterator begin, std::deque<char>::iterator end) {
    std::vector<std::string> tags;

    std::string tag;
    while(begin != end){
        if(*begin == '\0'){
            tags.push_back(tag);
            tag = "";
        } else {
            tag += *begin;
        }
        begin++;
    }
    return tags;
}

std::vector<Pixel> readPixels(std::deque<char>::iterator begin, std::deque<char>::iterator end) {
    std::vector<Pixel>pixels = std::vector<Pixel>();
    while(begin != end){
        unsigned int red = (unsigned char)*begin;
        begin++;
        unsigned int green = (unsigned char)*begin;
        begin++;
        unsigned int blue = (unsigned char)*begin;
        begin++;

        pixels.push_back(Pixel{red, green, blue});
    }

    return pixels;
}

Ciff parseCiff(std::deque<char>& bytes){
    std::string ciffConstant = "CIFF";
    for(int i = 0; i < 4; i++){
        if(ciffConstant[i] != bytes[i]){
            throw std::invalid_argument("No CIFF magic");
        }
    }

    size_t deletedBytes = 0;

    bytes.erase(bytes.begin(), bytes.begin() + 4);
    deletedBytes += 4;

    unsigned int headerSize = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    deletedBytes += 8;
    std::cout << "header_size: " << headerSize << std::endl;

    unsigned int contentSize = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    deletedBytes += 8;
    std::cout << "content_size: " << contentSize << std::endl;

    unsigned int width = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    deletedBytes += 8;

    std::cout << "width: "  << width << std::endl;

    unsigned int height = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    deletedBytes += 8;

    std::cout << "height: "  << height << std::endl;

    if(contentSize != width * height * 3){
        throw std::invalid_argument("content_size invalid");
    }

    std::string caption = readUntilNewLine(bytes.begin());
    bytes.erase(bytes.begin(), bytes.begin() + caption.length() + 1);
    deletedBytes += caption.length() + 1;
    std::cout << "caption: "  << caption << std::endl;

    unsigned int headerLengthRemain = headerSize - deletedBytes;
    std::vector<std::string> tags = readTags(bytes.begin(), bytes.begin() + headerLengthRemain);
    std::cout << "tags: ";
    for(const auto& tag : tags){
        std::cout << tag << " ";
    }
    std::cout << std::endl;
    bytes.erase(bytes.begin(),bytes.begin() + headerLengthRemain);

    std::vector<Pixel> pixels = readPixels(bytes.begin(), bytes.begin() + contentSize);
    bytes.erase(bytes.begin(),bytes.begin() + contentSize);
    std::cout << pixels.size() << std::endl;

    Ciff ciff = Ciff{width =  width, height = height, caption = caption, tags = tags, pixels = pixels};

    return ciff;
}

void createPng(const Ciff& ciff){
    int width = ciff.width;
    int height = ciff.height;

    std::vector<uint8_t> image;

    const char* filename = "data/ciff2.png";

    GifWriter writer = {};
    GifBegin(&writer, filename, width, height, 0);

    for (Pixel pixel : ciff.pixels){
        image.push_back(pixel.Red);
        image.push_back(pixel.Green);
        image.push_back(pixel.Blue);
        image.push_back(255);
    }

    GifWriteFrame(&writer, image.data(), width, height, 0);

    GifEnd(&writer);
}


void readCaffHeader(std::deque<char>& bytes, Caff& caff){
    unsigned int id = unsigned(bytes[0]);
    bytes.erase(bytes.begin(), bytes.begin() + 1);
    if(id != 1){
        throw std::invalid_argument("Invalid block ID in CAFF header:" + std::to_string(id));
    }

    unsigned int blockLength = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    if(blockLength != 20){
        throw std::invalid_argument("CAFF header should be 20 bytes long but it was:" + std::to_string(blockLength));
    }

    std::string caffStr = "CAFF";
    for(int i = 0; i < 4; i++){
        if(caffStr[i] != bytes[i]){
            throw std::invalid_argument("No CAFF magic");
        }
    }
    bytes.erase(bytes.begin(), bytes.begin() + 4);

    unsigned int headerSize = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);

    unsigned int numAnim = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    caff.numAnim = numAnim;
}

void readCreditsBlock(std::deque<char>& bytes, Caff& caff){
    std::cout << "--- read credits block" << std::endl;

    // TODO needed somewhere?
    // unsigned int blockLength = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);

    unsigned int year = readNumber(bytes.begin(), bytes.begin() + 2);
    bytes.erase(bytes.begin(), bytes.begin() + 2);
    caff.year = year;

    unsigned int month = readNumber(bytes.begin(), bytes.begin() + 1);
    bytes.erase(bytes.begin(), bytes.begin() + 1);
    caff.month = month;

    unsigned int day = readNumber(bytes.begin(), bytes.begin() + 1);
    bytes.erase(bytes.begin(), bytes.begin() + 1);
    caff.day = day;

    unsigned int hour = readNumber(bytes.begin(), bytes.begin() + 1);
    bytes.erase(bytes.begin(), bytes.begin() + 1);
    caff.hour = hour;

    unsigned int minute = readNumber(bytes.begin(), bytes.begin() + 1);
    bytes.erase(bytes.begin(), bytes.begin() + 1);
    caff.minute = minute;

    unsigned int creatorLen = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);

    if(creatorLen == 0){
        caff.creator = "";
        return;
    }

    std::string creator = readText(bytes.begin(), bytes.begin() + creatorLen);
    bytes.erase(bytes.begin(), bytes.begin() + creatorLen);
    caff.creator = creator;
}

void readAnimationsBlock(std::deque<char>& bytes, Caff& caff){
    std::cout << "--- read animations block" << std::endl;
    
    // TODO needed somewhere?
    // unsigned int blockLength = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);

    unsigned int duration = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);

    Ciff ciff = parseCiff(bytes);

    CiffAnimation ciffAnim = {
        duration,
        ciff,
    };

    caff.ciffAnimations.push_back(ciffAnim);
}

Caff parseCaff(std::string path){
    Caff caff = Caff();

    std::deque<char> bytes = readCaff(path);
    std::cout << "starting size:" << bytes.size() << std::endl;

    readCaffHeader(bytes, caff);
    
    while(bytes.size() > 0){
        unsigned blockId = unsigned(bytes[0]);
        bytes.erase(bytes.begin(), bytes.begin() + 1);

        switch (blockId)
        {
        case 1:
            throw std::invalid_argument("Another CAFF header?");
            break;
        case 2:
            readCreditsBlock(bytes, caff);
            break;
        case 3:
            readAnimationsBlock(bytes, caff);
            break;
        default:
            throw std::invalid_argument("Invalid block ID:" + std::to_string(blockId));
        }
    }

    return caff;
}

void createGif(Caff caff){
    int width = caff.ciffAnimations[0].ciff.width;
    int height = caff.ciffAnimations[0].ciff.height;

	auto fileName = "data/test.gif";
    
    GifWriter g;
    GifBegin(&g, fileName, width, height, 0);
    for(CiffAnimation ca : caff.ciffAnimations){
        std::cout << ca.duration << std::endl;
        int delay = ca.duration / 10;
        std::vector<Pixel> pixels = ca.ciff.pixels;
        std::vector<uint8_t> container;
        for (Pixel pixel : pixels){
            container.push_back(pixel.Red);
            container.push_back(pixel.Green);
            container.push_back(pixel.Blue);
            container.push_back(255);
        }

        GifWriteFrame(&g, container.data(), width, height, delay);
    }
	GifEnd(&g);
}

int main() {
    Caff caff = parseCaff("data/2.caff");
    createGif(caff);

    return 0;
}






