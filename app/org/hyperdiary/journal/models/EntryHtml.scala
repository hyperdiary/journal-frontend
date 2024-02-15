package org.hyperdiary.journal.models

import play.twirl.api.Html

case class EntryHtml(title: String, paragraphs: List[Html])
