from lib2to3.pgen2.token import EQUAL
import pytest
import utils
import main


def test1_empty_field():
    eq =""
    min ="5"
    max ="10"
    errs= []
    utils.Validation(eq , min ,max,errs) 
    #assert len(errs) != 0 
    assert errs[0] == utils.EMPTYERROR_FIELD

def test2_empty_field():
    eq ="x+3"
    min =""
    max ="10"
    errs= []
    utils.Validation(eq , min ,max,errs) 
    #assert len(errs) != 0 
    assert errs[0] == utils.EMPTYERROR_FIELD

def test3_empty_field():  
    eq ="x+3"
    min ="5"
    max =""
    errs= []
    utils.Validation(eq , min ,max,errs) 
    #assert len(errs) != 0 
    print(errs)
    assert errs[0] == utils.EMPTYERROR_FIELD

def test1_nonNumbricValue():
    eq = "x+3"
    min = "a"
    max = 5
    errs = []
    utils.Filter().filterNumbers(min,errs)
    assert errs[0] == utils.NONUMERIC_ERROR

def test2_nonNumbricValue():
    eq = "x+3"
    min = 1
    max = "a"
    errs = []
    utils.Filter().filterNumbers(max,errs)
    assert errs[0] == utils.NONUMERIC_ERROR


def test_invalid_equation():

    eq = "#x#$"
    min = 1
    max = 5
    errs = []
    utils.Filter().filterEquation(eq)
    utils.Validation(eq , min ,max,errs)    
    assert errs[0] == utils.INVALID_EQUATION


def test_successful1():
    eq = "x+3"
    min = "1"
    max = "5"
    errs = []
    utils.Filter().filterNumbers(max,errs)
    utils.Filter().filterEquation(eq)
    utils.Validation(eq , min ,max,errs) 
    assert len(errs) == 0

def test_successful2():
    eq = "x^2+5"
    min = "1"
    max = "5"
    errs = []
    utils.Filter().filterNumbers(max,errs)
    utils.Filter().filterEquation(eq)
    utils.Validation(eq , min ,max,errs) 
    print(errs)
    assert len(errs) == 0


