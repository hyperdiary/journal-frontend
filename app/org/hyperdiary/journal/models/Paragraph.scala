package org.hyperdiary.journal.models

case class Paragraph(index: Int, text: String)
object Paragraph {
  implicit val paragraphOrdering: Ordering[Paragraph] = Ordering.by(_.index)
}
