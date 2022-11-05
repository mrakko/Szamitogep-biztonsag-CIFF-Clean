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
    std::vector<Pixel> getPixels() const;
private:
    int width;
    int height;
    std::string caption;
    std::vector<std::string> tags;
    std::vector<Pixel> pixels;
};

struct CiffAnimation{
    unsigned int duration;
    Ciff ciff;
};

struct Caff{
    unsigned int numAnim;
    unsigned int year;
    unsigned int month;
    unsigned int day;
    unsigned int hour;
    unsigned int minute;
    std::string creator;
    std::vector<CiffAnimation> ciffAnimations;
};

#endif //CIFF_CLEAN_CIFF_H
