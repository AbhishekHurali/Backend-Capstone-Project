package com.example.ProductCatalogue.UnitTest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test// this allows the test method to run automatically by pressing the play button
    //methodName_when_then -> This is how the method name should be
    public void AdditionOn2Integers_RunsSuccessfully(){

        //In unit test input values are hardcoded.
        //Arrange
        Calculator calculator = new Calculator();

        //Action
        int result = calculator.add(1,9);

        //Assert
        assert(result==10);

        //can also be written as
        //assertEquals(10,result);

    }

    @Test
    public void DivideByZero_throwsException(){

        //arrange
        Calculator calculator = new Calculator();

        //Act and Assert
        //Since the divide method throws the exception, we can't hold the result in variable
        //so we use assertThrows(Exception.class, method to be called(lambda function)
        assertThrows(ArithmeticException.class,
                ()->calculator.divide(1,0));

    }


}