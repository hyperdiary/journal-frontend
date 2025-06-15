package org.hyperdiary.journal.forms

case class LabelData(labelText: String, targetGraph: String, targetName: String)
object LabelData {
  def unapply(label: LabelData): Option[(String, String, String)] = Some(
    (label.labelText, label.targetGraph, label.targetName)
  )
}
