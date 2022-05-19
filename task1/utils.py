import re
import numpy as np
import ast

## constants
EMPTYERROR_FIELD = "All fields Should be Filled"
NONUMERIC_ERROR = "Fill minX and MaxX filds should be numbers"
RANGE_ERROR = "maxX should be greater than minX"
INVALID_EQUATION = "invalid Equation"

EquationConversions = {
    "^": "**",
    " ":"",
    "sin": "np.sin",
    "cos": "np.cos",
    "tan": "np.tan",
    "exp": "np.exp",
    "sqrt": "np.sqrt",
    "log10":"np.log10"
}


class Filter:
    '''
    This class used for filtering equation and values of x 
    to be ready for validation.
    '''
    def __init__(self):
        pass


    def filterEquation(self,eq):
        eq = eq.strip()
        for k, v in EquationConversions.items():
            eq = eq.replace(k, v)
        return eq

    def filterNumbers(self,num,errs):
        num = num.strip()
        try:
            num = float(num)
            return num
        except:
            errs.append(NONUMERIC_ERROR)

class Validation:
    '''
    This class used for Validate equation and min and max value of x 
    '''
    def __init__(self, equation, minX, maxX,errs):
        
        self.checkEmpty(equation, minX, maxX,errs)
        if errs == []:
            self.ValidateEquation(equation,errs)
        if errs == []:    
            self.checkRange(minX, maxX,errs)

    def checkEmpty(self,equation, minX, maxX,errs):
        '''
        check that equation and min and max values are not empty
        '''
        if equation == "" or minX == "" or maxX == "":
            errs.append(EMPTYERROR_FIELD)

    def ValidateEquation(self,eq,errs):
        '''
        check that the equation is a valid one
        '''
        try:
            ast.parse(eq.lower())
        except :
            print("ho")
            errs.append(INVALID_EQUATION)

    def checkRange(self,minX, maxX,errs):
        if maxX < minX:
            errs.append(RANGE_ERROR)
