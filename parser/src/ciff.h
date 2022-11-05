//
// Created by Dell on 2022. 11. 04..
//

#ifndef CIFF_CLEAN_CIFF_H
#define CIFF_CLEAN_CIFF_H

#include <string>
#include <vector>

struct Pixel {
    unsigned int Red;
    unsigned int Green;
    unsigned int Blue;
};

struct Ciff{
    unsigned int width;
    unsigned int height;
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
