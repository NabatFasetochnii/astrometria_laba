import Core.Companion.PATH_TYCO2

class Core {
    companion object {
        const val PATH_TYCO2 = "C:\\Users\\Nikita\\IdeaProjects\\astrometria_laba\\src\\tyco2\\DATA\\catalog.dat"
        const val PATH_UCAC2 = "C:\\Users\\Nikita\\IdeaProjects\\astrometria_laba\\src\\ucac2\\1cd\\u2\\z"
        var pairs = 0
//        var o = 0
    }
}

fun main() {

    var runTime = System.nanoTime()

    val tStars = Stars()
    val uStars = Stars()
    val read = ReadCats()
    val pc = PairCalculation()

    read.readTYCO(PATH_TYCO2, tStars)

//    tStars.RaSys =
//    tStars.DeSys.addAll(MutableList(288) { mutableListOf()})

    systematize(tStars)

    for (i in 0..287) {
        uStars.deSys[i] = MutableList(tStars.deSys.size) { 0 }
        uStars.raSys[i] = MutableList(tStars.raSys.size) { 0 }
        uStars.magVSys[i] = MutableList(tStars.magVSys.size) { 0f }
    }

    println(tStars.dE.size)

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


    for (k in 0 until tStars.dE.size) {
        val z = ((tStars.dE[k] + zeroToEc) / halfDegToMas).toInt() //file number - 1

        a = tStars.rA[k]
        b = tStars.dE[k]
        c = tStars.magV[k]

        tStars.deSys[z].add(b)
        tStars.raSys[z].add(a)
        tStars.magVSys[z].add(c)
    }

}