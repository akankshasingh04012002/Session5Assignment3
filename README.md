
 Calculator with arithmetic operations and mathematical functions impementation uses trait-based design to define,validate input,execute operations asynchronously using Future abstraction
 The Validator trait provides a method validate that is implemented by each operator to check if the given operands satisfy the requirements for the operator. The Operator trait extends the Validator trait and provides a method validateAndExecute that validates the operands and performs the operation. It also provides a protected method execute that performs the actual computation of the operation.
trait Validator {
  def validate(operands: Seq[Double]): Boolean
}

trait Operator extends Validator {
  def validateAndExecute(operands: Seq[Double]): Seq[Double]

  protected def execute(operands: Seq[Double]): Seq[Double]
}

The Calculator object defines a calculate method that takes an operator and a sequence of operands and returns a Future containing the result of the operation. It uses pattern matching to determine which operator to use and then calls the execute method of the operator to perform the operation. If the operands are invalid, it returns a Future containing a CalculatorException

Each operator is implemented as a class that extends the Operator trait and overrides the validate and execute methods. Some operators also override the validateAndExecute method if they have additional validation requirements.
for example: the AddOperator class overrides the execute method to perform addition on the operands and returns a sequence containing the result. 


Additional Functionality Class :-

This is a Scala class named AdditionalFunctionality which extends the PowerOperator class. It provides additional functionality through the following methods:

squareOfExpression: This method takes in two operands as Double values and returns a string that indicates whether the square of the sum of the two operands is equal to the sum of the squares of the two operands and twice their product.

findAverageAfterChainingOperations: This method takes in a sequence of Double values and returns a future that calculates the average of the sum of all odd numbers from the Fibonacci sequence of each number in the input sequence.

find: This method takes in a sequence of Double values and returns a future that filters out all numbers whose factorials are greater than or equal to 6 raised to the power of the number itself.
Usage : To use the AdditionalFunctionality class, just simply created an instance of the class and call any of its methods.
 The code which is implemented has driver class which the one who execute the whole so here is the output of it also:
 
 ![Screenshot from 2023-04-13 21-05-02](https://user-images.githubusercontent.com/125342404/231835230-65d04cd2-de78-4ac9-9364-cb02313ff52a.png)
