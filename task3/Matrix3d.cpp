#include <iostream>
#include <vector>
#include <fstream>
#include <string>
using namespace std;

class Matrix
{
public:
    int dimX, dimY, dimZ;
    vector<vector<vector<int>>> mat;
    Matrix(int dimX, int dimY, int dimZ)
    {
        this->dimX = dimX;
        this->dimY = dimY;
        this->dimZ = dimZ;
        this->mat.resize(dimX, vector<vector<int>>(dimY, vector<int>(dimZ)));
    }

    void setMatrix(int dimX, int dimY, int dimZ, string filename)
    {
        ifstream inputFile;
        inputFile.open(filename, ios::in);
        string dummyLine;
        getline(inputFile, dummyLine);
        for (int i = 0; i < dimX; i++)
            for (int j = 0; j < dimY; j++)
                for (int k = 0; k < dimZ; k++)
                {
                    inputFile >> mat[i][j][k];
                    // cout<<mat[i][j][k]<<" ";
                }
    }
    bool Validate(int i, int j, int k)
    {
        return i >= 0 && i < dimX && j >= 0 && j < dimY && k >= 0 && k < dimZ;
    }

    void SetValues(int i, int j, int k, int val, bool &error)
    {
        if (Validate(i, j, k))
        {
            mat[i][j][k] = val;
        }
        else
            error = true;
    }

    int GetSize()
    {
        return dimX * dimY * dimZ;
    }

    int GetValues(int i, int j, int k, bool &error)
    {
        if (Validate(i, j, k))
        {
            return mat[i][j][k];
        }
        error = true;
        return -1;
    }

    void printMatrix3D()
    {
        for (int i = 0; i < dimX; ++i)
        {
            for (int j = 0; j < dimY; ++j)
            {
                for (int k = 0; k < dimZ; ++k)
                {
                    cout << mat[i][j][k] << " ";
                }
               cout<<"\n";
            }
            cout<<"\n";
        }
    }

    ~Matrix()
    {
        mat.clear();
    }
};