package org.hyperdiary.journal.controllers

import org.hyperdiary.journal.services.{ LabelService, PersonService }
import play.api.mvc.{ Action, AnyContent, BaseController, ControllerComponents, MessagesActionBuilder, MessagesRequest, Request }
import org.hyperdiary.journal.forms.{ KnowledgeGraph, LabelDataFormProvider }
import org.hyperdiary.journal.vocabulary.{ DBpedia, PersonalKnowledgeGraph, Wikidata }

import javax.inject.Inject
import scala.util.{ Failure, Success }

class LabelController @Inject() (
  messagesAction: MessagesActionBuilder,
  val controllerComponents: ControllerComponents,
  labelService: LabelService,
  pkg: PersonalKnowledgeGraph
) extends BaseController {

  def view(): Action[AnyContent] = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    Ok(
      org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider(), getKnowledgeGraphs)
    )

  }

  def submit(): Action[AnyContent] = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("add") =>
        LabelDataFormProvider()
          .bindFromRequest()
          .fold(
            formWithErrors =>
              BadRequest(org.hyperdiary.journal.views.html.labelMaker(formWithErrors, getKnowledgeGraphs)),
            labelData =>
              labelService.createLabel(labelData.labelText, s"${labelData.targetGraph}${labelData.targetName}") match {
                case Success(result) =>
                  Ok(org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider(), getKnowledgeGraphs))
                case Failure(e) =>
                  BadRequest(org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider(), getKnowledgeGraphs))
              }
          )
      case Some("finish") =>
        Ok(org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider(), getKnowledgeGraphs))
      case _ => Ok(org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider(), getKnowledgeGraphs))
    }
  }

  private def getKnowledgeGraphs: Seq[KnowledgeGraph] =
    List(
      KnowledgeGraph(DBpedia.resourceBaseUri, "DBpedia"),
      KnowledgeGraph(Wikidata.entityBaseUri, "Wikidata"),
      KnowledgeGraph(pkg.personBaseUri, "Hyperdiary person")
    )

}
