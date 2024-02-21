package org.hyperdiary.journal.models

import play.twirl.api.Html

case class JournalHtml (title:String, entries: List[EntryLink], subtitle: Option[String])
