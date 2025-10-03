package signal
import scala.language.implicitConversions

object Implicits {
  inline implicit def variantToVariantSelector(
      v: Variant
  ): VariantSelector =
    _ => v
}

object Converters {
  given modToValue[C <: Singleton, T](using
      v: ValueOf[C]
  ): Conversion[C => T, T] with {
    def apply(f: C => T): T = f(v.value)
  }
}

trait Signal[+A] {
  def now(): A
  def map[B](project: A => B): Signal[B]
}

class Val[A](constantValue: A) extends Signal[A] {
  def now(): A = constantValue
  def map[B](project: A => B): Signal[B] = new Val(project(constantValue))
}

import Implicits.{variantToVariantSelector}

enum Variant {
  case Primary, Secondary
}

type VariantSelector = Variant.type => Variant

case class Button(variant: Variant)

object Button {
  transparent inline def apply(
      inline variant: VariantSelector = _.Primary
  ): Button = {
    Button(
      variant = variant(Variant)
    )
  }
}

@main def run() = {
  val button4 = Button(variant = Variant.Secondary)
  println(button4)
}
