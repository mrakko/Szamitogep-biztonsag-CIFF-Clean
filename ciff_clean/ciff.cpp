//
// Created by Dell on 2022. 11. 04..
//

#include "ciff.h"
#include <iostream>

Ciff::Ciff(unsigned int w, unsigned int h, std::string &c, std::vector<std::string> &t)
        : width(w), height(h), caption(c), tags(t) {}

void Ciff::setPixels(std::vector<Pixel> &p) {
    if (p.size() != width * height) {
        throw std::invalid_argument("Wrong pixel count");
    }
    pixels = p;
}

