package org.hyperdiary.journal.controllers

import org.hyperdiary.journal.services.{LabelService, PersonService}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, MessagesActionBuilder, MessagesRequest, Request}
import org.hyperdiary.journal.forms.{KnowledgeGraph, LabelDataFormProvider, LabelUriFormProvider}
import org.hyperdiary.journal.vocabulary.{DBpedia, PersonalKnowledgeGraph, Wikidata}

import javax.inject.Inject
import scala.concurrent.Future
import scala.util.{Failure, Success}

class LabelController @Inject() (
  messagesAction: MessagesActionBuilder,
  val controllerComponents: ControllerComponents,
  labelService: LabelService,
  pkg: PersonalKnowledgeGraph
) extends BaseController {

  def home(): Action[AnyContent] = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    Ok(
      org.hyperdiary.journal.views.html.label()
    )
  }

  def navigate(): Action[AnyContent] = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("create") =>
        Ok(
          org.hyperdiary.journal.views.html.labelCreate(LabelDataFormProvider(), getKnowledgeGraphs)
        )
      case Some("delete") =>
        Ok(
          org.hyperdiary.journal.views.html.labelDelete(LabelUriFormProvider())
        )
      case _ =>
        ???
        Ok(
          org.hyperdiary.journal.views.html.label()
        )
    }
  }

  def delete(): Action[AnyContent] = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    Ok(
      org.hyperdiary.journal.views.html.labelDelete(LabelUriFormProvider())
    )
  }

  def deleteLabel(): Action[AnyContent] = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("delete") =>
        LabelUriFormProvider()
          .bindFromRequest()
          .fold(
            formWithErrors => BadRequest(org.hyperdiary.journal.views.html.labelDelete(formWithErrors)),
            labelUri =>
              labelService.deleteLabel(labelUri.uri) match {
                case Success(_) =>
                  Ok(
                    org.hyperdiary.journal.views.html
                      .labelDelete(LabelUriFormProvider(), Some(s"${pkg.labelBaseUri}${labelUri.uri}"))
                  )
                case Failure(e) => BadRequest(e.getMessage)
              }
          )
      case Some("cancel") => Ok(org.hyperdiary.journal.views.html.label())
      case _              => ???
    }
  }

  def create(): Action[AnyContent] = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    Ok(
      org.hyperdiary.journal.views.html.labelCreate(LabelDataFormProvider(), getKnowledgeGraphs)
    )
  }

  def submit(): Action[AnyContent] = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("create") =>
        LabelDataFormProvider()
          .bindFromRequest()
          .fold(
            formWithErrors =>
              BadRequest(org.hyperdiary.journal.views.html.labelCreate(formWithErrors, getKnowledgeGraphs)),
            labelData =>
              labelService.createLabel(labelData.labelText, s"${labelData.targetGraph}${labelData.targetName}") match {
                case Success(labelUri) =>
                  Ok(org.hyperdiary.journal.views.html.labelCreate(LabelDataFormProvider(), getKnowledgeGraphs, Some(labelUri)))
                case Failure(e) =>
                  BadRequest(org.hyperdiary.journal.views.html.labelCreate(LabelDataFormProvider(), getKnowledgeGraphs))
              }
          )
      case Some("cancel") => Ok(org.hyperdiary.journal.views.html.label())
      case _ => Ok(org.hyperdiary.journal.views.html.labelCreate(LabelDataFormProvider(), getKnowledgeGraphs))
    }
  }

  def getLink(label: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val htmlLink = labelService.getHtmlHyperlink(label)
    Future.successful(Ok(htmlLink))
  }

  private def getKnowledgeGraphs: Seq[KnowledgeGraph] =
    List(
      KnowledgeGraph(DBpedia.resourceBaseUri, "DBpedia"),
      KnowledgeGraph(Wikidata.entityBaseUri, "Wikidata"),
      KnowledgeGraph(pkg.personBaseUri, "Hyperdiary person")
    )

}
