import Core.Companion.dA
import Core.Companion.dD
import Core.Companion.starsSize

class DeltaCalc {

    fun bigSysDelta() {
        var deltaA = 0.0
        var deltaD = 0.0

        for (j in 0 until dA.size) {
            for (i in dA[j]) {
                deltaA += i / starsSize
            }

            for (i in dD[j]) {
                deltaD += i / starsSize
            }
        }

        dCal(deltaA, deltaD)

    }

    fun deltaGridA() {

        var deltaA = 0.0
        var deltaD = 0.0
        var s = 0

        for (i in 0..8) {
            for (j in i * 30..30 * (i + 1)) {
                for (k in i * 30..30 * (i + 1)){
                    s+= dA[k].size
                }

                for (p in 0 until dA[j].size) {
                    for (h in dA[j]) {
                        deltaA +=  h / s
                    }

                    for (h in dD[j]) {
                        deltaD += i / s
                    }
                }
            }
        }

        dCal(deltaA, deltaD)

    }
}

fun dCal(deltaA: Double, deltaD: Double){

    for (j in 0 until dA.size) {
        for (i in 0 until dA[j].size) {
            dA[j][i] -= deltaA
        }

        for (i in 0 until dD.size) {
            dD[j][i] -= deltaD
        }
    }
}