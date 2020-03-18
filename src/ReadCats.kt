import Core.Companion.dA
import Core.Companion.dD
import Core.Companion.pairs
import java.io.DataInputStream
import java.io.File
import java.io.IOException
import kotlin.math.abs

class ReadCats {

    fun readUCAC(tStars: Stars, i: Int, uStars: Stars, stream: DataInputStream) {

        var ra: Int
        var de: Int
        var mag: Float
        var b: Boolean
        val skipB = 34L

        stream.mark(50_000_000)

        for (j in 0 until tStars.raSys[i].size) {

            b = true

            while (b) {
                try {

                    ra = Integer.reverseBytes(stream.readInt())
                    de = Integer.reverseBytes(stream.readInt())
                    mag = java.lang.Short.reverseBytes(stream.readShort()) / 100f

                    if (abs(tStars.raSys[i][j] - ra) < 1000
                        && abs(tStars.deSys[i][j] - de) < 1000
                        && abs(tStars.magVSys[i][j] - mag) < 1
                    ) {
                        uStars.raSys[i].add(j, ra)
                        uStars.deSys[i].add(j, de)
                        uStars.magVSys[i].add(j, mag)
                        dA.add((tStars.raSys[i][j] - ra).toDouble())
                        dD.add((tStars.deSys[i][j] - de).toDouble())
                        //dM.add((tStars.magSys[i][j] - mag).toDouble())
                        pairs++
                        print(pairs)
                        println(" - pairs")
                        try {
                            stream.reset()
                        } catch (e: Exception) {
                            println("reset is not working........")
                        }

                        b = false
//                    break
                    } else {
                        stream.skip(skipB)
                    }
                } catch (ex: IOException) {
                    /* o++
                       println("Поймал исключение IOException $o раз")*/
                    if (stream.available() == 0) {
                        stream.reset()
                        b = false
                    } else {
                        stream.skip(skipB)
                    }
                }

            }

        }
    }


    //TODO mag

    fun readTYCO(PATH_TYCO2: String, tStars: Stars) {

        val file = File(PATH_TYCO2)

        var v: Float
        var b: Float
        var de: Double
        var ra: Double

        file.forEachLine {
            try {
                de = it.subSequence(28, 40).toString().toDouble()
                if (de <= 54.0) {

                    ra = it.subSequence(15, 27).toString().toDouble()
                    v = it.subSequence(110, 116).toString().toFloat()
                    b = it.subSequence(123, 129).toString().toFloat()

                    ra *= 3600_000.0
                    de *= 3600_000.0
                    tStars.rA.add(ra.toInt())
                    tStars.dE.add(de.toInt())
                    tStars.magV.add(v - 0.09f * (b - v))

                    //TODO mag

                }
            } catch (e: Exception) { /*println("exception in forEachLine")*/
            }
        }


//54deg
    }

}
