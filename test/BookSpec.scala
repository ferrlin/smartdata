import model.Book
import org.joda.time.DateTime
import org.scalatestplus.play.PlaySpec

/**
  * Created by jferrolino on 26/4/17.
  */
class BookSpec extends PlaySpec {

  "A Book " must {
    val book = Book(isbn = "",
      title = "",
      author = "",
      blurb = "",
      publisher = "",
      publishDate = DateTime.now(),
      genres = Set())

    "throw a IllegalArgumentException when isbn is not supplied" in {
      a[IllegalArgumentException] must be thrownBy {
        val noISBN = book.copy(isbn = null)
      }
    }

    "throw an IllegalArgumentException when title is not supplied" in {
      a[IllegalArgumentException] must be thrownBy {
        val noTitle = book.copy(title = null)
      }
    }

    "throw an IllegalArgumentException when author is not supplied" in {
      a[IllegalArgumentException] must be thrownBy {
        val noAuthor = book.copy(author = null)
      }
    }

    "throw an IllegalArgumentException when blurb is not supplied" in {
      a[IllegalArgumentException] must be thrownBy {
        val noBlurb = book.copy(blurb = null)
      }
    }

    "throw an IllegalArgumentException when publish date is not supplied" in {
      a[IllegalArgumentException] must be thrownBy {
        val noPublishDate = book.copy(publishDate = null)
      }
    }

    "throw an IllegalArgumentException when genres are not supplied" in {
      a[IllegalArgumentException] must be thrownBy {
        val noGenre = book.copy(genres = null)
      }
    }

    "will proceed even publisher is not provided" in {
      val noPublisher = book.copy(publisher = null)
      noPublisher.publisher mustBe null
    }
  }
}
