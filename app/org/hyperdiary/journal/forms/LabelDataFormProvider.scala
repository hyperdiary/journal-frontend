package org.hyperdiary.journal.forms

import play.api.data.Form
import play.api.data.Forms.{ mapping, text }
import play.api.data.validation.Constraints.nonEmpty

object LabelDataFormProvider {

  def apply(): Form[LabelData] = Form(
    mapping(
      "label-text"   -> text.verifying(nonEmpty),
      "target-graph" -> text,
      "target-name"  -> text.verifying(nonEmpty)
    )(LabelData.apply)(LabelData.unapply)
  )

}
