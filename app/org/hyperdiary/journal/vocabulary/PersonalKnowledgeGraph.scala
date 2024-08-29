package org.hyperdiary.journal.vocabulary

case class PersonalKnowledgeGraph(uriPrefix: String) {
  val baseUri = s"http://$uriPrefix.hyperdiary.io"
  val labelBaseUri = s"$baseUri/label/"
  val personBaseUri = s"$baseUri/person/"
  val residenceBaseUri = s"$baseUri/residence/"
  val thingBaseUri = s"$baseUri/thing/"
  val photoBaseUri = s"$baseUri/photo/"
}
