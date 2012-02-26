package controllers

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.User

trait Users {
  implicit def user(implicit session: Session): Option[User] = {
    for (name <- session.get(Users.USERNAME_KEY)) yield User(name)
  }
}

object Users extends Controller {
  
  private val USERNAME_KEY = "username"
  
  private def referrerOrIndex(implicit request: RequestHeader) = {
    request.headers.get(REFERER).getOrElse(routes.Application.index.url)
  }
  
  def login = Action { implicit request =>
    val form = Form(single("name"->nonEmptyText))
    form.bindFromRequest.fold(
      error => Redirect(referrerOrIndex),
      name  => Redirect(referrerOrIndex).withSession(USERNAME_KEY -> name)
    )
  }

  def logout = Action { implicit request =>
    Redirect(referrerOrIndex).withNewSession
  }

}