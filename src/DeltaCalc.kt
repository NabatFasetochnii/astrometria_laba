import Core.Companion.dA2
import Core.Companion.dD2
import Core.Companion.starsSize
import kotlin.math.pow
import kotlin.math.sqrt

class DeltaCalc {

    fun bigSysDelta(d: MutableList<MutableList<Double>>) {
        var delta = 0.0

        for (j in 0 until d.size) {
            for (i in d[j]) {
                delta += i / starsSize
            }
        }
        dCal(delta, d)
    }

    fun deltaMag(uStars: Stars) { //5 to 16 mag
        var deltaA = 0.0
        var deltaD = 0.0
        var s: Int

        val daArray: MutableList<Double> = mutableListOf()
        val ddArray: MutableList<Double> = mutableListOf()
        val magArray: MutableList<Float> = mutableListOf()

        val arrayOfArrayDa: MutableList<MutableList<Double>> = MutableList(11) { MutableList(0) { 0.0 } }
        val arrayOfArrayDd: MutableList<MutableList<Double>> = MutableList(11) { MutableList(0) { 0.0 } }

        for (i in 0 until 288) {
            for (j in 0 until dA2[i].size) {
                daArray.add(dA2[i][j])
                ddArray.add(dD2[i][j])
                magArray.add(uStars.magVSys2[i][j])
            }
        }

        for (i in 0 until magArray.size) {
            when (magArray[i]) {

                in 5f..6f -> {
                    arrayOfArrayDa[0].add(daArray[i])
                    arrayOfArrayDd[0].add(ddArray[i])
                }
                in 6f..7f -> {
                    arrayOfArrayDa[1].add(daArray[i])
                    arrayOfArrayDd[1].add(ddArray[i])
                }
                in 7f..8f -> {
                    arrayOfArrayDa[2].add(daArray[i])
                    arrayOfArrayDd[2].add(ddArray[i])
                }
                in 8f..9f -> {
                    arrayOfArrayDa[3].add(daArray[i])
                    arrayOfArrayDd[3].add(ddArray[i])
                }
                in 9f..10f -> {
                    arrayOfArrayDa[4].add(daArray[i])
                    arrayOfArrayDd[4].add(ddArray[i])
                }
                in 10f..11f -> {
                    arrayOfArrayDa[5].add(daArray[i])
                    arrayOfArrayDd[5].add(ddArray[i])
                }
                in 11f..12f -> {
                    arrayOfArrayDa[6].add(daArray[i])
                    arrayOfArrayDd[6].add(ddArray[i])
                }
                in 12f..13f -> {
                    arrayOfArrayDa[7].add(daArray[i])
                    arrayOfArrayDd[7].add(ddArray[i])
                }
                in 13f..14f -> {
                    arrayOfArrayDa[8].add(daArray[i])
                    arrayOfArrayDd[8].add(ddArray[i])
                }
                in 14f..15f -> {
                    arrayOfArrayDa[9].add(daArray[i])
                    arrayOfArrayDd[9].add(ddArray[i])
                }
                in 15f..16f -> {
                    arrayOfArrayDa[10].add(daArray[i])
                    arrayOfArrayDd[10].add(ddArray[i])
                }
            }
        }

        for (i in 0 until arrayOfArrayDa.size) {
            s = arrayOfArrayDa[i].size
            for (j in 0 until s) {
                deltaA += arrayOfArrayDa[i][j] / s
                deltaD += arrayOfArrayDd[i][j] / s
            }
        }

        dCal(deltaA, dA2)
        dCal(deltaD, dD2)
    }

    fun deltaGridA(uStars: Stars) {

        var deltaA = 0.0
        var deltaD = 0.0
        var s: Int

        //кошмар
        val daArray: MutableList<Double> = mutableListOf()
        val ddArray: MutableList<Double> = mutableListOf()
        val aArray: MutableList<Int> = mutableListOf()
        //val arrayOfArray: MutableList<MutableList<Int>> = MutableList(24) { MutableList(0) { 0 } }
        val arrayOfArrayDA: MutableList<MutableList<Double>> = MutableList(24) { MutableList(0) { 0.0 } }
        val arrayOfArrayDD: MutableList<MutableList<Double>> = MutableList(24) { MutableList(0) { 0.0 } }

        for (i in 0 until 288) {
            for (j in 0 until dA2.size) {
                try {
                    daArray.add(dA2[i][j])
                    ddArray.add(dD2[i][j])
                    aArray.add(uStars.raSys2[i][j])
                } catch (ex: Exception) {
                }

            }
        }
        for (i in 0 until aArray.size) {
            when (i * 0.001 / 60.0 / 60.0) {

                in 0.0..1.0 -> {
                    //arrayOfArray[0].add(aArray[i])
                    arrayOfArrayDA[0].add(daArray[i])
                    arrayOfArrayDD[0].add(ddArray[i])
                }
                in 1.0..2.0 -> {
                    //arrayOfArray[1].add(aArray[i])
                    arrayOfArrayDA[1].add(daArray[i])
                    arrayOfArrayDD[1].add(ddArray[i])
                }
                in 2.0..3.0 -> {
                    //arrayOfArray[2].add(aArray[i])
                    arrayOfArrayDA[2].add(daArray[i])
                    arrayOfArrayDD[2].add(ddArray[i])
                }
                in 3.0..4.0 -> {
                    //arrayOfArray[3].add(aArray[i])
                    arrayOfArrayDA[3].add(daArray[i])
                    arrayOfArrayDD[3].add(ddArray[i])
                }
                in 4.0..5.0 -> {
                    //arrayOfArray[4].add(aArray[i])
                    arrayOfArrayDA[4].add(daArray[i])
                    arrayOfArrayDD[4].add(ddArray[i])
                }
                in 5.0..6.0 -> {
                    //arrayOfArray[5].add(aArray[i])
                    arrayOfArrayDA[5].add(daArray[i])
                    arrayOfArrayDD[5].add(ddArray[i])
                }
                in 6.0..7.0 -> {
                    //arrayOfArray[6].add(aArray[i])
                    arrayOfArrayDA[6].add(daArray[i])
                    arrayOfArrayDD[6].add(ddArray[i])
                }
                in 7.0..8.0 -> {
                    //arrayOfArray[7].add(aArray[i])
                    arrayOfArrayDA[7].add(daArray[i])
                    arrayOfArrayDD[7].add(ddArray[i])
                }
                in 8.0..9.0 -> {
                    //arrayOfArray[8].add(aArray[i])
                    arrayOfArrayDA[8].add(daArray[i])
                    arrayOfArrayDD[8].add(ddArray[i])
                }
                in 9.0..10.0 -> {
                    //arrayOfArray[9].add(aArray[i])
                    arrayOfArrayDA[9].add(daArray[i])
                    arrayOfArrayDD[9].add(ddArray[i])
                }
                in 10.0..11.0 -> {
                    //arrayOfArray[10].add(aArray[i])
                    arrayOfArrayDA[10].add(daArray[i])
                    arrayOfArrayDD[10].add(ddArray[i])
                }
                in 11.0..12.0 -> {
                    //arrayOfArray[11].add(aArray[i])
                    arrayOfArrayDA[11].add(daArray[i])
                    arrayOfArrayDD[11].add(ddArray[i])
                }
                in 12.0..13.0 -> {
                    //arrayOfArray[12].add(aArray[i])
                    arrayOfArrayDA[12].add(daArray[i])
                    arrayOfArrayDD[12].add(ddArray[i])
                }
                in 13.0..14.0 -> {
                    //arrayOfArray[13].add(aArray[i])
                    arrayOfArrayDA[13].add(daArray[i])
                    arrayOfArrayDD[13].add(ddArray[i])
                }
                in 14.0..15.0 -> {
                    //arrayOfArray[14].add(aArray[i])
                    arrayOfArrayDA[14].add(daArray[i])
                    arrayOfArrayDD[14].add(ddArray[i])
                }
                in 15.0..16.0 -> {
                    //arrayOfArray[15].add(aArray[i])
                    arrayOfArrayDA[15].add(daArray[i])
                    arrayOfArrayDD[15].add(ddArray[i])
                }
                in 16.0..17.0 -> {
                    //arrayOfArray[16].add(aArray[i])
                    arrayOfArrayDA[16].add(daArray[i])
                    arrayOfArrayDD[16].add(ddArray[i])
                }
                in 17.0..18.0 -> {
                    //arrayOfArray[17].add(aArray[i])
                    arrayOfArrayDA[17].add(daArray[i])
                    arrayOfArrayDD[17].add(ddArray[i])
                }
                in 18.0..19.0 -> {
                    //arrayOfArray[18].add(aArray[i])
                    arrayOfArrayDA[18].add(daArray[i])
                    arrayOfArrayDD[18].add(ddArray[i])
                }
                in 19.0..20.0 -> {
                    //arrayOfArray[19].add(aArray[i])
                    arrayOfArrayDA[19].add(daArray[i])
                    arrayOfArrayDD[19].add(ddArray[i])
                }
                in 20.0..21.0 -> {
                    //arrayOfArray[20].add(aArray[i])
                    arrayOfArrayDA[20].add(daArray[i])
                    arrayOfArrayDD[20].add(ddArray[i])
                }
                in 21.0..22.0 -> {
                    //arrayOfArray[21].add(aArray[i])
                    arrayOfArrayDA[21].add(daArray[i])
                    arrayOfArrayDD[21].add(ddArray[i])
                }
                in 22.0..23.0 -> {
                    //arrayOfArray[22].add(aArray[i])
                    arrayOfArrayDA[22].add(daArray[i])
                    arrayOfArrayDD[22].add(ddArray[i])
                }
                in 23.0..24.0 -> {
                    //arrayOfArray[23].add(aArray[i])
                    arrayOfArrayDA[23].add(daArray[i])
                    arrayOfArrayDD[23].add(ddArray[i])
                }

            }
        }

        for (i in 0 until arrayOfArrayDA.size) {
            s = arrayOfArrayDA[i].size
            for (j in 0 until s) {
                deltaA += arrayOfArrayDA[i][j] / s
                deltaD += arrayOfArrayDD[i][j] / s
            }
        }

        dCal(deltaA, dA2)
        dCal(deltaD, dD2)
    }

    fun deltaGridD() {

        var deltaA = 0.0
        var deltaD = 0.0
        var s = 0

        for (i in 0..13) {
            for (k in i * 20 until 20 * (i + 1)) {
                s += dA2[k].size
            }
            for (j in i * 20 until 20 * (i + 1)) {
                for (h in dA2[j]) {
                    deltaA += h / s
                }
                for (h in dD2[j]) {
                    deltaD += h / s
                }
            }
        }
        s = 0
        for (k in 281 until 288) {
            s += dA2[k].size
        }
        for (j in 281 until 288) {
            for (h in dA2[j]) {
                deltaA += h / s
            }
            for (h in dD2[j]) {
                deltaD += h / s
            }
        }
        dCal(deltaA, dA2)
        dCal(deltaD, dD2)
    }

    private fun dCal(delta: Double, d: MutableList<MutableList<Double>>) {

        for (j in 0 until d.size) {
            for (i in 0 until d[j].size) {
                d[j][i] -= delta
            }
        }
    }

    fun finalDeltaCalc(): List<Double> {
        var da = 0.0
        var dd = 0.0
        for (i in 0 until 288) {
            for (j in 0 until dA2[i].size) {
                da = dA2[i][j].pow(2.0)
                dd = dD2[i][j].pow(2.0)
            }
        }
        da = sqrt(da) / dA2.size
        dd = sqrt(dd) / dD2.size

        return listOf(da, dd)
    }
}