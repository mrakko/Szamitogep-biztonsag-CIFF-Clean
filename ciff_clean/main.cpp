#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include "ciff.h"
#include "gif.h"

std::vector<char> readCiff(const std::string& fileName){
    std::ifstream input(fileName, std::ios::binary);

    std::vector<char> bytes(
            (std::istreambuf_iterator<char>(input)),
            (std::istreambuf_iterator<char>()));
    input.close();

    //TODO
    bytes.erase(bytes.begin(), bytes.begin()+83);

    return bytes;
}

std::vector<char> readCaff(std::string fileName){
    std::ifstream input(fileName, std::ios::binary);

    std::vector<char> bytes(
            (std::istreambuf_iterator<char>(input)),
            (std::istreambuf_iterator<char>()));
    input.close();

    return bytes;
}

unsigned int readNumber(std::vector<char>::iterator begin, std::vector<char>::iterator end) {
    int result = 0;
    unsigned int j = 0;
    for(auto i = begin; i != end; i++, j++){
        unsigned int byte = (unsigned char)*i;
        result += byte * pow(16, 2*j);
    }

    return result;
}

std::string readUntilNewLine(std::vector<char>::iterator iterator) {
    std::string caption;
    while(*iterator != '\n'){
        caption += *iterator;
        iterator++;
    }
    return caption;
}

std::vector<std::string> readTags(std::vector<char>::iterator begin, std::vector<char>::iterator end) {
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

std::vector<Ciff::Pixel> readPixels(std::vector<char>::iterator begin, std::vector<char>::iterator end) {
    std::vector<Ciff::Pixel>pixels = std::vector<Ciff::Pixel>();
    while(begin != end){
        unsigned int red = (unsigned char)*begin;
        begin++;
        unsigned int green = (unsigned char)*begin;
        begin++;
        unsigned int blue = (unsigned char)*begin;
        begin++;

        pixels.push_back(Ciff::Pixel{red, green, blue});
    }

    return pixels;
}

Ciff parseCiff(std::vector<char>& bytes){


    std::string ciffConstant = "CIFF";
    for(int i = 0; i < 4; i++){
        if(ciffConstant[i] != bytes[i]){
            throw std::invalid_argument("No CIFF magic");
        }
    }

    bytes.erase(bytes.begin(), bytes.begin() + 4);


    unsigned int headerSize = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    std::cout << "header_size: " << headerSize << std::endl;

    unsigned int contentSize = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    std::cout << "content_size: " << contentSize << std::endl;

    unsigned int width = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    std::cout << "width: "  << width << std::endl;

    unsigned int height = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    std::cout << "height: "  << height << std::endl;

    if(contentSize != width * height * 3){
        throw std::invalid_argument("content_size invalid");
    }

    std::string caption = readUntilNewLine(bytes.begin());
    bytes.erase(bytes.begin(), bytes.begin() + caption.length() + 1);
    std::cout << "caption: "  << caption << std::endl;

    unsigned int headerLengthRemain = headerSize - (36 + caption.length() + 1);
    std::vector<std::string> tags = readTags(bytes.begin(), bytes.begin() + headerLengthRemain);
    std::cout << "tags: ";
    for(const auto& tag : tags){
        std::cout << tag << " ";
    }
    std::cout << std::endl;
    bytes.erase(bytes.begin(),bytes.begin() + headerLengthRemain);

    std::vector<Ciff::Pixel> pixels = readPixels(bytes.begin(), bytes.begin() + contentSize);
    bytes.erase(bytes.begin(),bytes.begin() + contentSize);
    std::cout << pixels.size() << std::endl;

    Ciff ciff = Ciff(width, height, caption, tags);
    ciff.setPixels(pixels);

    return ciff;
}

<<<<<<< HEAD
void createPng(const Ciff& ciff){
    int width = ciff.getWidth();
    int height = ciff.getHeight();
    uint8_t image[ width * height * 4 ];

    const char* filename = "../ciff2.png";

    GifWriter writer = {};
    GifBegin(&writer, filename, width, height, 2);

    for(int y=0; y<height; y++)
    {
        for(int x=0; x<width; x++)
        {
            auto pixel = ciff.getPixel(x, y);
            auto red = (uint8_t)pixel.Red;
            auto green = (uint8_t)pixel.Green;
            auto blue = (uint8_t)pixel.Blue;

            image[(y*width+x)*4] = red;
            image[(y*width+x)*4 + 1] = green;
            image[(y*width+x)*4 + 2] = blue;
            //image[(y*width+x)*4 + 3] = 255;
        }
    }

    GifWriteFrame(&writer, image, width, height, 2);

    GifEnd(&writer);
}

=======

void readCaffHeader(std::vector<char>& bytes){
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

    std::string caff = "CAFF";
    for(int i = 0; i < 4; i++){
        if(caff[i] != bytes[i]){
            throw std::invalid_argument("No CAFF magic");
        }
    }
    bytes.erase(bytes.begin(), bytes.begin() + 4);

    unsigned int headerSize = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    std::cout << "header_size: " << headerSize << std::endl;

    unsigned int numAnim = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    std::cout << "num_anim: " << numAnim << std::endl;
}

void readCreditsBlock(std::vector<char>& bytes){
    std::cout << "read credits block" << std::endl;

    unsigned int year = readNumber(bytes.begin(), bytes.begin() + 2);
    bytes.erase(bytes.begin(), bytes.begin() + 2);
    std::cout << "year:" << year << std::endl;

    unsigned int month = readNumber(bytes.begin(), bytes.begin() + 1);
    bytes.erase(bytes.begin(), bytes.begin() + 1);
    std::cout << "month:" << month << std::endl;

    unsigned int day = readNumber(bytes.begin(), bytes.begin() + 1);
    bytes.erase(bytes.begin(), bytes.begin() + 1);
    std::cout << "day:" << day << std::endl;

    unsigned int hour = readNumber(bytes.begin(), bytes.begin() + 1);
    bytes.erase(bytes.begin(), bytes.begin() + 1);
    std::cout << "hour:" << hour << std::endl;

    unsigned int minute = readNumber(bytes.begin(), bytes.begin() + 1);
    bytes.erase(bytes.begin(), bytes.begin() + 1);
    std::cout << "minute:" << minute << std::endl;

    unsigned int creatorLen = readNumber(bytes.begin(), bytes.begin() + 8);
    bytes.erase(bytes.begin(), bytes.begin() + 8);
    std::cout << "creatorLen:" << creatorLen << std::endl;


}

void readAnimationsBlock(std::vector<char>& bytes){
    std::cout << "read animations block" << std::endl;

    unsigned int duration = readNumber(bytes.begin(), bytes.begin() + 2);
    bytes.erase(bytes.begin(), bytes.begin() + 2);
    std::cout << "duration:" << duration << std::endl;

    // TODO read CIFF
}

void parseCaff(){
    std::vector<char> bytes = readCaff("data\\1.caff");
    std::cout << "starting size:" << bytes.size() << std::endl;

    readCaffHeader(bytes);
    
    // while(???){
        unsigned blockId = unsigned(bytes[0]);
        bytes.erase(bytes.begin(), bytes.begin() + 1);

        switch (blockId)
        {
        case 1:
            throw std::invalid_argument("Another CAFF header?");
            break;
        case 2:
            readCreditsBlock(bytes);
            break;
        case 3:
            readAnimationsBlock(bytes);
            break;
        default:
            throw std::invalid_argument("Invalid block ID:" + std::to_string(blockId));
        }
    // }


}


>>>>>>> 9df5835e722f53116834c978d155db31701378e1
int main() {
    std::vector<char> bytes = readCiff("../2.caff");
    Ciff ciff = parseCiff(bytes);
    bytes.erase(bytes.begin(),bytes.begin() + 17);
    Ciff ciff2 = parseCiff(bytes);

    createPng(ciff);

    return 0;
}






