package org.hyperdiary.journal.forms

import play.api.data.Form
import play.api.data.Forms.{ mapping, text }
import play.api.data.validation.Constraints.nonEmpty

object LabelDataFormProvider {

  def apply(): Form[LabelData] = Form(
    mapping(
      FieldNames.labelText   -> text.verifying(nonEmpty),
      FieldNames.targetGraph -> text,
      FieldNames.targetName  -> text.verifying(nonEmpty)
    )(LabelData.apply)(LabelData.unapply)
  )

  object FieldNames {
    val labelText = "label-text"
    val targetGraph = "target-graph"
    val targetName = "target-name"
  }

}
