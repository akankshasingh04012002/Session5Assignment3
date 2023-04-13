import java.lang.Math._
import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait Validator {
  def validate(operands: Seq[Double]): Boolean
}

trait Operator extends Validator {
  def validateAndExecute(operands: Seq[Double]): Seq[Double]

  protected def execute(operands: Seq[Double]): Seq[Double]
}

case class CalculatorException(message: String) extends Exception(message)

object Calculator {
  def calculate(operator: String, operands: Seq[Double]): Future[Seq[Double]] = {
    operator match {
      case "+" => execute(new AddOperator, operands)
      case "-" => execute(new SubtractOperator, operands)
      case "*" => execute(new MultiplyOperator, operands)
      case "/" => execute(new DivideOperator, operands)
      case "^" => execute(new PowerOperator, operands)
      case "sqrt" => execute(new SquareRootOperator, operands)
      case "!" => execute(new FactorialOperator, operands)
      case "sum" => execute(new SumOperator, operands)
      case "gcd" => execute(new GcdOperator, operands)
      case "odd" => execute(new OddOperator, operands)
      case "even" => execute(new EvenOperator, operands)
      case "fibonacci" => execute(new FibonacciOperator, operands)
      case _ => Future.failed(CalculatorException("Invalid operator"))
    }
  }

  private def execute(operator: Operator, operands: Seq[Double]): Future[Seq[Double]] = {
    if (operator.validate(operands)) {
      Future.successful(operator.validateAndExecute(operands))
    } else {
      Future.failed(CalculatorException("Invalid operands"))
    }
  }
}

class AddOperator extends Operator {
  override def validate(operands: Seq[Double]): Boolean = operands.length == 2

  override def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override protected def execute(operands: Seq[Double]): Seq[Double] = {
    val result = operands.reduce(_ + _)
    Seq(result)
  }
}

class SubtractOperator extends Operator {
  override def validate(operands: Seq[Double]): Boolean = operands.length == 2

  override def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override protected def execute(operands: Seq[Double]): Seq[Double] = {
    val result = operands.head - operands.last
    Seq(result)
  }
}

class MultiplyOperator extends Operator {
  override def validate(operands: Seq[Double]): Boolean = operands.length == 2

  override def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override protected def execute(operands: Seq[Double]): Seq[Double] = {
    val result = operands.reduce(_ * _)
    Seq(result)
  }
}

class DivideOperator extends Operator {
  override def validate(operands: Seq[Double]): Boolean = operands.length == 2 && operands.last != 0

  override def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override protected def execute(operands: Seq[Double]): Seq[Double] = {
    val result = operands.head / operands.last
    Seq(result)
  }
}

class PowerOperator extends Operator {
  override def validate(operands: Seq[Double]): Boolean = operands.length == 2

  override def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override protected def execute(operands: Seq[Double]): Seq[Double] = {
    @tailrec
    def power(value: Double, num: Double, accumulator: Double): Double = {
      if (num == 0) accumulator
      else power(value, num - 1, value * accumulator)
    }

    val result = power(operands.head, operands.last, 1)
    Seq(result)
  }
}

class SquareRootOperator extends Operator {
  def validate(operands: Seq[Double]): Boolean = operands.length == 1 && operands(0) >= 0

  def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  protected def execute(operands: Seq[Double]): Seq[Double] = Seq(math.sqrt(operands(0)))
}

class FactorialOperator extends Operator {
  def validate(operands: Seq[Double]): Boolean = operands.length == 1 && operands(0) >= 0 && operands(0) == operands(0).toInt

  def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override protected def execute(operands: Seq[Double]): Seq[Double] = {
    if (operands.length != 1) throw new IllegalArgumentException("Factorial operator requires exactly one operand")
    Seq(factorial(operands.head))
  }

  private def factorial(number: Double): Double = {
    if (number < 0) throw new IllegalArgumentException("Factorial of negative numbers is undefined")

    @tailrec
    def loop(num: Double, accumulator: Double): Double =
      if (num <= 1) accumulator else loop(num - 1, num * accumulator)

    loop(number, 1)
  }
}

class SumOperator extends Operator {
  override def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override def validate(operands: Seq[Double]): Boolean = operands.nonEmpty

  override protected def execute(operands: Seq[Double]): Seq[Double] = Seq(operands.reduce(_ + _))
}

class FibonacciOperator extends Operator {
  override def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override def validate(operands: Seq[Double]): Boolean = operands.length == 1 && operands.head.toInt > 0

  override protected def execute(operands: Seq[Double]): Seq[Double] = {
    val n = operands.head.toInt

    @tailrec
    def fibonacci(num: Int, prev: Int, curr: Int, seq: Seq[Int]): Seq[Int] = {
      if (num <= 0) seq
      else fibonacci(num - 1, curr, curr + prev, seq :+ curr)
    }

    fibonacci(n, 0, 1, Seq.empty[Int]).map(_.toDouble)
  }
}

class EvenOperator extends Operator {

  override def validate(operands: Seq[Double]): Boolean = operands.nonEmpty

  override def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override protected def execute(operands: Seq[Double]): Seq[Double] = operands.filter(_ % 2 == 0)

}

class OddOperator extends Operator {
  override def validate(operands: Seq[Double]): Boolean = operands.nonEmpty

  override def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override protected def execute(operands: Seq[Double]): Seq[Double] = {
    operands.filter(_ % 2 != 0)
  }
}

class GcdOperator extends Operator {

  override def validate(operands: Seq[Double]): Boolean = operands.length == 2

  override def validateAndExecute(operands: Seq[Double]): Seq[Double] = execute(operands)

  override protected def execute(operands: Seq[Double]): Seq[Double] = {

    @tailrec
    def gcd(firstValue: Double, secondValue: Double): Double = {
      if (secondValue == 0) firstValue
      else gcd(secondValue, firstValue % secondValue)
    }

    Seq(gcd(operands.head, operands.last))
  }
}

class AdditionalFunctionality extends PowerOperator {

  def squareOfExpression(firstOperand: Double, secondOperand: Double): String = {
    val leftSide = math.pow(firstOperand + secondOperand, 2)
    val rightSide = math.pow(firstOperand, 2) + math.pow(secondOperand, 2) + (2 * firstOperand * secondOperand)
    if (leftSide == rightSide) "Equal" else "Not Equal"
  }

  def findAverageAfterChainingOperations(numbers: Seq[Double]): Future[Double] = {
    Future {
      val fibonacciSeq = numbers.map(num => fibonacci(num.toInt))
      val oddNumbersSeq = fibonacciSeq.map(seq => seq.filter(num => num % 2 != 0))
      val oddNumbersSum = oddNumbersSeq.map(seq => seq.sum)
      oddNumbersSum.sum.toDouble / numbers.length
    }
  }

  private def fibonacci(num: Int): Seq[Int] = {
    @tailrec
    def fibHelper(num: Int, prev: Int, curr: Int, seq: Seq[Int]): Seq[Int] = num match {
      case 0 => seq
      case 1 => seq :+ 1
      case _ => fibHelper(num - 1, curr, prev + curr, seq :+ (prev + curr))
    }

    fibHelper(num, 0, 1, Seq(0))
  }

  def find(numbers: Seq[Double]): Future[Seq[Double]] = {
    Future {
      numbers.filter { num =>
        val fact = factorial(num.toInt)
        fact <= pow(6, num)
      }
    }
  }

  private def factorial(num: Double): Double = {
    if (num == 0) 1
    else num * factorial(num - 1)
  }
}




