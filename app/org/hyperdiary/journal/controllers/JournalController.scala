package org.hyperdiary.journal.controllers

import org.hyperdiary.journal.services.JournalService
import play.api.*
import play.api.mvc.*

import javax.inject.*

/** This controller creates an `Action` to handle HTTP requests to the application's home page.
  */
@Singleton
class JournalController @Inject() (
  val controllerComponents: ControllerComponents,
  journalService: JournalService
) extends BaseController {

  /** Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method will be called when the application receives a `GET`
    * request with a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(org.hyperdiary.journal.views.html.index())
  }

  def view(journalId: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    journalService.getJournal(journalId) match {
      case Some(journal) =>
        Ok(org.hyperdiary.journal.views.html.journal(journal))
      case _ => NotFound("No journal found")
    }
  }

  def viewEntry(journalId: String, entryId: String): Action[AnyContent] =
    Action { implicit request: Request[AnyContent] =>
      journalService.getEntry(journalId, entryId) match {
        case Some(entry) => Ok(org.hyperdiary.journal.views.html.entry(entry))
        case _           => NotFound("No entry found")
      }
    }

}
