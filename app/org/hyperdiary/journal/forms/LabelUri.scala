package org.hyperdiary.journal.forms

case class LabelUri(uri: String)
object LabelUri {
  def unapply(labelUri: LabelUri): Option[String] = Some(
    labelUri.uri
  )
}
