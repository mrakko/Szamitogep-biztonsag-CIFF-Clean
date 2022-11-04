#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include "ciff.h"

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

int main() {
    std::vector<char> bytes = readCiff("D:\\msc2\\SzgBizt\\2.caff");
    Ciff ciff = parseCiff(bytes);
    bytes.erase(bytes.begin(),bytes.begin() + 17);
    Ciff ciff2 = parseCiff(bytes);

    return 0;
}




