package com.giocc.tensor

import com.giocc.tensor.dense.DenseTensorView

import scala.reflect.ClassTag
import scala.{specialized => sp}

/**
  * Represents an K-rank tensor with elements of type A.
  *
  * @tparam A The type of the elements stored in the tensor.
  */
trait Tensor[@sp A] {

  /**
    * The shape of the tensor.
    */
  def shape: Shape

  /**
    * Given an index, retrieves the element at the given index.
    *
    * @param index The index.
    * @return The element at the given index.
    */
  def apply(index: Int): A

  /**
    * Given an index and a value, replaces the element at the given index with the given value.
    *
    * @param index The index.
    * @param value The value to substitute with.
    */
  def update(index: Int, value: A): Unit

  /**
    * Given subscript, retrieves the element at the given subscript.
    *
    * @param subscript The subscript.
    * @return The element at given subscript.
    */
  def apply(subscript: Subscript): A

  /**
    * Given a subscript and a value, replaces the element at the given subscript with the given value.
    *
    * @param subscript The subscript.
    * @param value     The value to substitute with.
    */
  def update(subscript: Subscript, value: A): Unit

  /**
    * The optimal style of indexing.
    */
  def indexStyle: IndexStyle

  def iterator: Iterator[A]

  def toArray[B >: A](implicit ev: ClassTag[B]): Array[B] = {
    iterator.toArray
  }

  /**
    * Given a subscript map, constructs a tensor view that maps its subscripts using the subscript map.
    *
    * @param subscriptMap The subscript map.
    * @return The tensor view.
    */
  def view(subscriptMap: SubscriptMap): DenseTensorView[A] = {
    new DenseTensorView(this, subscriptMap)
  }

  override def equals(o: Any): Boolean = {
    o match {
      case that: Tensor[A] =>
        val it1 = iterator
        val it2 = that.iterator
        while (it1.hasNext && it2.hasNext) if (it1.next != it2.next) return false
        !it1.hasNext && !it2.hasNext
      case _ =>
        false
    }
  }

  override def hashCode(): Int = {
    val it = iterator
    var h = 1
    while (it.hasNext) {
      val elem = it.next()
      h = 31 * h + (if (elem == null) 0 else elem.hashCode())
    }
    h
  }
}