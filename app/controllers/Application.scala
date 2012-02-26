package controllers

import play.api.mvc._
import models.User

object Application extends Controller with Users {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def show = Action { implicit request =>
    Ok(views.html.show("42"))
  }

}