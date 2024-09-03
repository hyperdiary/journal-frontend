package org.hyperdiary.journal.forms

import play.api.data.Form
import play.api.data.Forms.{ mapping, text }

object LabelDataFormProvider {

  def apply(): Form[LabelData] = Form(
    mapping(
      "text" -> text,
      "graph" -> text,
      "name" -> text
    )(LabelData.apply)(LabelData.unapply)
  )

}
