//
// Created by Dell on 2022. 11. 04..
//

#include "ciff.h"
#include <iostream>
#include <vector>

Ciff::Ciff(unsigned int w, unsigned int h, std::string &c, std::vector<std::string> &t)
        : width(w), height(h), caption(c), tags(t) {}

void Ciff::setPixels(std::vector<Pixel> &p) {
    if (p.size() != width * height) {
        throw std::invalid_argument("Wrong pixel count");
    }
    pixels = p;
}

int Ciff::getHeight() const {
    return this->height;
}

int Ciff::getWidth() const {
    return this->width;
}

Ciff::Pixel Ciff::getPixel(int x, int y) const {
    return this->pixels.at(y * height + x);
}

std::vector<Ciff::Pixel> Ciff::getPixels() const {
    return this->pixels;
}
