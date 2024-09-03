package org.hyperdiary.journal.forms

case class LabelData(text: String, graph: String, name: String)
object LabelData {
  def unapply(label: LabelData): Option[(String, String, String)] = Some((label.text, label.graph, label.name))
}
