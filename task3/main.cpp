#include <iostream>
#include "./FlatternMatrix.cpp"
#include <fstream>
#include <ostream>
using namespace std;

int main()
{
    string fileName;
    bool error = false;
    int n,m,k;
    
    cout << "welcome to 3D Matrix Converter\n";
    cout << "Enter filename : ";
    
    cin >> fileName;
    ifstream indata;
    indata.open(fileName, ios::in);
    indata >> n>>m>>k;

    Matrix * matrix3d = new Matrix(n,m,k);
    // set matrix3D value
    matrix3d->setMatrix(n,m,k,fileName);
    // print the 3D Matrix
    matrix3d->printMatrix3D();

    // get the value of the matrix at location : 0 1 0
    int val = matrix3d->GetValues(0,0,1,error);
    if(!error)
        cout<<val<<"\n";
    
    // get invalid matrix3d location
    val = matrix3d->GetValues(3,2,1,error);
    if(error){
        cout<<"invalid index\n";
        error = false;
    }
    else  cout<<val<<"\n";

    // set the value of the matrix at location (0,0,0) to new value
     matrix3d->SetValues(0,0,0,5,error);

     // print the 3D Matrix
    matrix3d->printMatrix3D();

    // convert the 3D Matrix to 1D flatternMatrix
    FlatternMatrix *flatMatrix = new FlatternMatrix(matrix3d);
    flatMatrix->PrintFlattenMatrix();

    // get matrix value at
    val = flatMatrix->GetValueAt(0,0,0,error);
    if(error){
        cout<<"invalid index\n";
        error = false;
    }
    else  cout<<val<<"\n";

    // update matrix value at
    flatMatrix->SetValueAt(0,0,1,9,error);
    if(error){
        cout<<"invalid index\n";
        error = false;
    }
    flatMatrix->PrintFlattenMatrix();

    delete flatMatrix;
    delete matrix3d;
    indata.close();
}