package org.hyperdiary.journal.forms

import play.api.data.Form
import play.api.data.Forms.{ mapping, text }
import play.api.data.validation.Constraints.nonEmpty

object LabelUriFormProvider {

  def apply(): Form[LabelUri] = Form(
    mapping(
      "uri" -> text.verifying(nonEmpty)
    )(LabelUri.apply)(LabelUri.unapply)
  )

}
