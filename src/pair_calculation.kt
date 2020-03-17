import Core.Companion.PATH_UCAC2
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.File

class PairCalculation {

    fun cal(i: Int, tStars: Stars, uStars: Stars, readCats: ReadCats) {

        val z = i + 1
        var s = "$z"

        if (z in 1..9) {
            s = "00$z"
        } else if (z in 10..99) {
            s = "0$z"
        }
        val stream = DataInputStream(BufferedInputStream((File("$PATH_UCAC2$s").inputStream())))
        readCats.readUCAC(tStars, i, uStars, stream)

        stream.close()

    }
}

/*
fun twoMass2us(j: Double, h: Double): Double{
    return ((j-h+0.045)*0.98)
}*/
