import Core.Companion.PATH_TYCO2
import Core.Companion.dA
import Core.Companion.starsSize

class Core {
    companion object {
        const val PATH_TYCO2 = "C:\\Users\\Nikita\\IdeaProjects\\astrometria_laba\\src\\tyco2\\DATA\\catalog.dat"
        const val PATH_UCAC2 = "C:\\Users\\Nikita\\IdeaProjects\\astrometria_laba\\src\\ucac2\\1cd\\u2\\z"
        var pairs = 0
//        var o = 0
        var dA: MutableList<MutableList<Double>> = MutableList(288){ MutableList(0){0.0} }
        var dD: MutableList<MutableList<Double>> = MutableList(288){ MutableList(0){0.0} }
        var starsSize: Int = 0
    }
}

fun main() {

    var runTime = System.nanoTime()

    val tStars = Stars()
    val uStars = Stars()
    val read = ReadCats()
    val pc = PairCalculation()
    val deltaCalc = DeltaCalc()

    read.readTYCO(PATH_TYCO2, tStars)

    systematize(tStars)

    for (i in 0..287)   {
        uStars.deSys[i] = MutableList(tStars.deSys[i].size) { 0 }
        uStars.raSys[i] = MutableList(tStars.raSys[i].size) { 0 }
        uStars.magVSys[i] = MutableList(tStars.magVSys[i].size) { 0f }
        dA[i]= MutableList(tStars.deSys[i].size){0.0}
    }
    val runnable1 = Runnable {

        for (i in 0 until 288 step 4) {
            pc.cal(i, tStars, uStars, read)

        }


    }
    val runnable2 = Runnable {

        for (i in 1 until 288 step 4) {
            pc.cal(i, tStars, uStars, read)

        }
    }
    val runnable3 = Runnable {

        for (i in 2 until 288 step 4) {
            pc.cal(i, tStars, uStars, read)

        }
    }
    val thread1 = Thread(runnable1)
    val thread2 = Thread(runnable2)
    val thread3 = Thread(runnable3)
    thread1.start()
    thread2.start()
    thread3.start()
    for (i in 3 until 288 step 4) {
        pc.cal(i, tStars, uStars, read)
    }

    thread1.join()
    thread2.join()
    thread3.join()

    var runTime1 = (System.nanoTime() - runTime)/-1_000_000L
    println("$runTime1 - time pairs calc in ms")
    runTime1 /= 60_000L
    println("$runTime1 - time pairs calc in m")

    deltaCalc.bigSysDelta()
    deltaCalc.deltaGridA()
    deltaCalc.deltaGridD()

    runTime -= System.nanoTime()
    runTime /= -1_000_000L
    println("$runTime - time ms")
    runTime /= 60_000L
    println("$runTime - time m")
}

fun systematize(tStars: Stars) {

    var a: Int
    var b: Int
    var c: Float

    val zeroToEc = 90 * 3600_000.0
    val halfDegToMas = 0.5 * 3600_000.0

    starsSize = tStars.dE.size

    for (k in 0 until starsSize) {
        val z = ((tStars.dE[k] + zeroToEc) / halfDegToMas).toInt() //file number - 1

        a = tStars.rA[k]
        b = tStars.dE[k]
        c = tStars.magV[k]

        tStars.deSys[z].add(b)
        tStars.raSys[z].add(a)
        tStars.magVSys[z].add(c)
    }

}