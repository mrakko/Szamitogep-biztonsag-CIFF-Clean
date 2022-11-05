//
// Created by Dell on 2022. 11. 04..
//

#ifndef CIFF_CLEAN_CIFF_H
#define CIFF_CLEAN_CIFF_H

#include <string>
#include <vector>


class Ciff{
public:
    struct Pixel {
        unsigned int Red;
        unsigned int Green;
        unsigned int Blue;
    };
    Ciff(unsigned int w, unsigned int h, std::string& c, std::vector<std::string>& t);
    void setPixels(std::vector<Pixel>& p);
    int getWidth() const;
    int getHeight() const;
    Pixel getPixel(int x, int y) const;
private:
    int width;
    int height;
    std::string caption;
    std::vector<std::string> tags;
    std::vector<Pixel> pixels;
};


#endif //CIFF_CLEAN_CIFF_H
