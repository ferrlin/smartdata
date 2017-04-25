package controllers

import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import model.{Book, BookDao}
import org.joda.time.DateTime

class BookController @Inject() (bookDao: BookDao) extends Controller {

  def getHardcoded = Action {
    val book = Book(
      "123-45678",
      "Programming in Scala",
      "Martin Odersky, Lex Spoon, and Bill Venners",
      "Artima is very pleased to publish a new edition of the best-selling book on Scala, " +
      "written by the designer of the language, Martin Odersky. Co-authored by Lex Spoon and Bill Venners, " +
      "this book takes a step-by-step tutorial approach to teaching you Scala. Starting with the fundamental " +
      "elements of the language, Programming in Scala introduces functional programming from the practitioner's " +
      "perspective, and describes advanced language features that can make you a better, more productive developer.",
      "Artima",
      DateTime.parse("2010-12-13"),
      Set("programming", "handbook")
    )
    Ok(Json.toJson(book))
  }

  def get(bookId: String) = Action.async {
    bookDao.getBookById(bookId) map {
      case None => NotFound
      case Some(book) => Ok(Json.toJson(book))
    }
  }

  def populate() = Action.async {
    /* You can easily convert this endpoint to a bulk insert. Simply parse a `List[Book]` from
       JSON body and pass it instead of `cannedBulkInput` here. */
    bookDao.bulkIndex(cannedBulkInput) map {
      case resp if !resp.hasFailures => Ok
      case resp => InternalServerError(resp.failures.map(f => f.failureMessage) mkString ";")
    }
  }

  def search(q: String) = Action.async {
    bookDao.searchByQueryString(q) map {
      case books if books.length > 0 =>
        Ok(Json.toJson(books)).withHeaders("X-Total-Count" -> books.length.toString)
      case empty => NoContent
    }
  }

  val cannedBulkInput = List(
    Book(
      "978-0486298238",
      "Meditations",
      "Marcus Aurelius",
      "One of the world's most famous and influential books, Meditations, by the Roman emperor Marcus Aurelius" +
      "(A.D. 121–180), incorporates the stoic precepts he used to cope with his life as a warrior " +
      "and administrator of an empire.",
      "Dover Publications",
      DateTime.parse("1997-07-11").toDateTimeISO,
      Set("Philosophy", "Classics", "Nonfiction")),
    Book(
      "978-0451524935",
      "1984",
      "George Orwell",
      "The year 1984 has come and gone, but George Orwell's prophetic, nightmarish vision in 1949 of" +
      "the world we were becoming is timelier than ever.",
      "Signet Classics",
      DateTime.parse("1950-07-01").toDateTimeISO,
      Set("Politics", "Fiction")),
    Book(
      "978-0547928227",
      "The Hobbit",
      "J.R.R. Tolkien",
      "Bilbo Baggins is a hobbit who enjoys a comfortable, unambitious life, rarely traveling any farther than" +
      "his pantry or cellar. But his contentment is disturbed when the wizard Gandalf and a company of dwarves" +
      "arrive on his doorstep one day to whisk him away on an adventure. They have launched a plot to raid the" +
      "treasure hoard guarded by Smaug the Magnificent, a large and very dangerous dragon. Bilbo reluctantly joins" +
      "their quest, unaware that on his journey to the Lonely Mountain he will encounter both a magic ring and a" +
      "frightening creature known as Gollum.",
      "Houghton Mifflin Harcourt",
      DateTime.parse("2012-11-18").toDateTimeISO,
      Set("Fantasy", "Fiction")
    ))
}
