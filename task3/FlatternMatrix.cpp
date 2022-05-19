#include <iostream>
#include <vector>
#include "Matrix3d.cpp"
using namespace std;

class FlatternMatrix
{
public:
    vector<int> FlatMatrix;
    int n, m, k;
    int q;
    int GetNewIndex(int x, int y, int z)
    {
        // (z * xMax * yMax) + (y * xMax) + x
        return x * m * k + y * k + z;
    }

    void SetMatrix(Matrix *mat, int q)
    {
        bool error = false;
        int currentValue, newIndex;
        for (int x = 0; x < mat->dimX; x++)
        {
            for (int y = 0; y < mat->dimY; y++)
            {
                for (int z = 0; z < mat->dimZ; z++)
                {
                    currentValue = mat->GetValues(x, y, z, error);
                    if (!error)
                    {
                        newIndex = GetNewIndex(x, y, z);
                        FlatMatrix[newIndex] = currentValue;
                    }
                }
            }
        }
    }


    FlatternMatrix(Matrix *mat)
    {
         n = mat->dimX, m = mat->dimY, k = mat->dimX;
        // size of 1dMatrix
        q = mat->GetSize();
        FlatMatrix.resize(q);
        SetMatrix(mat, q);
    }

    void PrintFlattenMatrix()
    {
        cout << "Flattern Matrix:\n";
        for (int val : FlatMatrix)
            cout << val << " ";
        cout << "\n";
    }

    bool ValidateIndex(int index){
        return index >= 0 && index < this->q;
    }

    void SetValueAt(int i,int j ,int k,int val,bool &error){
        int index1D = GetNewIndex(i,i,k);
        if(ValidateIndex(index1D)){
            this->FlatMatrix[index1D] = val;
        }
        else{
            error = true;
        }
    }

    int GetValueAt(int i,int j ,int k,bool &error){
        int index1D = GetNewIndex(i,i,k);
        if(ValidateIndex(index1D))
            return this->FlatMatrix[index1D];
        else{
            error = true;
            return -1;
        }
    }

    ~FlatternMatrix()
    {
        FlatMatrix.clear();
    }
};
