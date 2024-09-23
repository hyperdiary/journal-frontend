package org.hyperdiary.journal

abstract class FrontendError extends Throwable

case class LabelAlreadyExists() extends Throwable
