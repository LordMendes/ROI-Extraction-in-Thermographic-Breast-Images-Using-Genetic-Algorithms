#ifndef COORDINATE_H
#define COORDINATE_H

#include <string>

class Coordinate {
public:
    int decimal;
    std::string binary;
    std::string gray_code;

    Coordinate(int n);

    std::string flipBit(const std::string& binary, int index);

    void mutate();

    std::string grayToBinary(const std::string& n);

    std::string intToBinary(int n);

    int binaryToDecimal(const std::string& binary);

    std::string binaryToGray(const std::string& n);
};

#endif // COORDINATE_H
