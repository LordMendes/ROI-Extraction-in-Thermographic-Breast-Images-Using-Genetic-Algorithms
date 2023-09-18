#include <iostream>
#include <cmath>
#include <random>
#include <string>
#include <bitset>

const double mutation_rate = 0.5;
const int ARRAY_SIZE = 10;

class Coordinate
{
public:
  int decimal;
  std::string binary;
  std::string gray_code;

  Coordinate(int n) : decimal(n), binary(intToBinary(n)), gray_code(binaryToGray(binary)) {}

  std::string flipBit(const std::string &binary, int index)
  {
    std::string binaryCopy = binary;
    if (binaryCopy[index] == '0')
    {
      binaryCopy[index] = '1';
    }
    else
    {
      binaryCopy[index] = '0';
    }
    return binaryCopy;
  }

  void mutate()
  {
    int mutationIndex = std::rand() % gray_code.length();

    gray_code = flipBit(gray_code, mutationIndex);
    binary = grayToBinary(gray_code);
    decimal = binaryToDecimal(binary);
  }

  std::string grayToBinary(const std::string &n)
  {
    int num = std::stoi(n, 0, 2);
    int mask = num;
    while (mask != 0)
    {
      mask >>= 1;
      num ^= mask;
    }
    std::string binaryString = std::bitset<ARRAY_SIZE>(num).to_string();
    return binaryString;
  }

  std::string intToBinary(int n)
  {
    std::string binaryString = "";
    while (n > 0)
    {
      binaryString = std::to_string(n % 2) + binaryString;
      n /= 2;
    }

    while (binaryString.length() < ARRAY_SIZE)
    {
      binaryString = "0" + binaryString;
    }

    return binaryString;
  }

  int binaryToDecimal(const std::string &binary)
  {
    int decimalValue = 0;
    int binaryLength = binary.length();

    for (int i = binaryLength - 1; i >= 0; i--)
    {
      if (binary[i] == '1')
      {
        decimalValue += static_cast<int>(pow(2, binaryLength - 1 - i));
      }
    }

    return decimalValue;
  }

  std::string binaryToGray(const std::string &n)
  {
    int num = std::stoi(n, 0, 2);
    num ^= (num >> 1);
    std::string grayString = std::bitset<ARRAY_SIZE>(num).to_string();
    return grayString;
  }
};
