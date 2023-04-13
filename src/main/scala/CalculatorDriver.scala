import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object CalculatorDriver {

  def main(args: Array[String]): Unit = {
    val calculator = new AdditionalFunctionality

    println(Calculator.calculate("+", Seq(1, 2)))
    println(Calculator.calculate("-", Seq(10, 2)))
    println(Calculator.calculate("*", Seq(2, 3)))
    println(Calculator.calculate("/", Seq(24, 2)))
    println(Calculator.calculate("!", Seq(10)))
    println(Calculator.calculate("sqrt", Seq(16)))
    println(Calculator.calculate("^", Seq(2, 2)))
    println(Calculator.calculate("fibonacci", Seq(5)))
    println(Calculator.calculate("sum", Seq(1, 2)))
    println(Calculator.calculate("even", Seq(1, 2, 3, 4)))
    println(Calculator.calculate("odd", Seq(1, 2, 3, 4)))
    println(Calculator.calculate("gcd", Seq(12, 18)))

    //Additional Functionality Implementations
    println(calculator.squareOfExpression(2, 3))

    // Testing find method
    val findNumbers: Seq[Double] = Seq(2.0, 3.0, 4.0, 5.0, 6.0)
    val findFuture: Future[Seq[Double]] = calculator.find(findNumbers)
    val findResult: Seq[Double] = Await.result(findFuture, 10.seconds)
    println(s"Result found is: $findResult")

    // Testing findAverageAfterChainingOperations method
    val averageNumbers = Seq(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
    val averageFuture: Future[Double] = calculator.findAverageAfterChainingOperations(averageNumbers)
    val averageResult = Await.result(averageFuture, 5.seconds)
    println(s"Average of odd Fibonacci numbers : $averageResult")

  }

}

