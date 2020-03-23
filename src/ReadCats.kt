import Core.Companion.PATH_OUT_FILE_T
import Core.Companion.PATH_OUT_FILE_U
import Core.Companion.dA
import Core.Companion.dD
import Core.Companion.pairs
import java.io.*
import kotlin.math.abs

class ReadCats {

    fun readUCAC(tStars: Stars, i: Int, uStars: Stars, stream: DataInputStream) {

        var ra: Int
        var de: Int
        var mag: Float
        var b: Boolean
        val skipB = 34L //Количество байт, которое мы пропускаем, чтобы перейти к началу следующей записи

        stream.mark(50_000_000) //Устанавливаем точку начала чтения,
        // к которой мы будем возвращаться,
        // когда найдём пару и передаём в функцию кол-во байт,
        // через которое этот маркер перестанет быть легитимным

        for (j in 0 until tStars.raSys[i].size) { //Для каждой йотой звезды в нашем итом массиве
            b = true

            while (b) {//Перебираем файл пока не найдём совпадение. Опыт показал, что while-false
                // работает лучше чем for-break
                try { //Если запись в файле вызывает исключение, то мы её игнорируем и идём дальше

                    ra = Integer.reverseBytes(stream.readInt()) //Читаем альфа, дельта и магнитуду
                    de = Integer.reverseBytes(stream.readInt())
                    mag = java.lang.Short.reverseBytes(stream.readShort()) / 100f

                    if (abs(tStars.raSys[i][j] - ra) < 1000 //проверяем на совпадение с учётом некоторой точности
                        && abs(tStars.deSys[i][j] - de) < 1000
                        && abs(tStars.magVSys[i][j] - mag) < 1
                    ) {
                        uStars.raSys[i].add(j, ra) //Записываем совпавшие звёзды в итый список на йотое место в нём,
                        // чтобы созранить индексацию
                        uStars.deSys[i].add(j, de)
                        uStars.magVSys[i].add(j, mag)
                        dA[i].add(j, (tStars.raSys[i][j] - ra).toDouble()) //Так же, фиксируем разность
                        dD[i].add(j, (tStars.deSys[i][j] - de).toDouble())
                        //dM.add((tStars.magSys[i][j] - mag).toDouble())
                        pairs++ //считаем количество пар

                        /*println("ra")
                        println(ra)
                        println(tStars.raSys[i][j])

                        println("de")
                        println(de)
                        println(tStars.deSys[i][j])

                        println("mag")
                        println(mag)
                        println(tStars.magVSys[i][j])*/

                        print(pairs)
                        println(" - pairs")
//                        println()
                        try {
                            stream.reset()//возвращаем каретку к маркеру
                        } catch (e: Exception) {
                            println("reset is not working........") //никогда не вызывается чему я рад
                        }
                        b = false //выходим из цикла, чтобы перейти к следующей записи
//                    break
                    } else {
                        stream.skip(skipB) //Если совпадения нет, то идём по файлу дальше
                    }
                } catch (ex: IOException) {
                    /* o++
                       println("Поймал исключение IOException $o раз")*/
                    if (stream.available() == 0) { //Проверяем конец файла
                        stream.reset()
                        b = false
                    } else {
                        stream.skip(skipB)
                    }
                }
            }
        }
    }

    fun readTYCO(PATH_TYCO2: String, tStars: Stars) { //функция для чтения каталога TYCO2

        val file = File(PATH_TYCO2)

        var v: Float
        var b: Float
        var de: Double
        var ra: Double

        file.forEachLine {
            try { //Для каждой строки файла я делаю следующее
                de = it.subSequence(28, 40).toString().toDouble() //читаю склонение и перевожу его в Double
                if (de <= 54.0) { //если оно ниже 54 градуса, то продолжаем работу со сторокой

                    ra = it.subSequence(15, 27).toString().toDouble() //читаем альфа
                    v = it.subSequence(110, 116).toString()
                        .toFloat() //читаем внутреннюю звёздную величину в одной полосе
                    b = it.subSequence(123, 129).toString()
                        .toFloat() //читаем внутреннюю звёздную величину в другой полосе

                    ra *= 3600_000.0 //переводим всё в миллисекунды
                    de *= 3600_000.0
                    tStars.rA.add(ra.toInt()) //Записываем в конец списка альфа и дельта
                    tStars.dE.add(de.toInt())
                    tStars.magV.add(v - 0.09f * (b - v)) //приближённо переводим внутренние магнитуды
                    // в магнитуду в полосе пропускания V и записываем в конец списка
                }
            } catch (e: Exception) { /*println("exception in forEachLine")*/ //Если возникает исключение
                // при работе с файлом, то мы исключаем из рассмотрения проблемную строку и идём дальше
            }
        }
//54deg
    }

    fun writeCats(uStars: Stars, tStars: Stars) {


        val ft = BufferedWriter(FileWriter(PATH_OUT_FILE_T, false)) //Открываем файловый поток
        val fu = BufferedWriter(FileWriter(PATH_OUT_FILE_U, false))

        for (i in 0 until 288) { //Перебираем каждую зону
            ft.write("$i - zone:") //пишем ее номер начиная с нуля
            ft.write("\n")
            fu.write("$i - zone:")
            fu.write("\n")

            for (j in 0 until tStars.raSys2[i].size) { //Перебираем каждую звезду в итой зоне
                try {
                    ft.write((tStars.raSys2[i][j]).toString()) //Записываем данные
                    ft.write(" ")
                    ft.write((tStars.deSys2[i][j]).toString())
                    ft.write(" ")
                    ft.write(tStars.magVSys2[i][j].toString())
                    ft.write("\n")

                    fu.write(uStars.raSys2[i][j].toString())
                    fu.write(" ")
                    fu.write(uStars.deSys2[i][j].toString())
                    fu.write(" ")
                    fu.write(uStars.magVSys2[i][j].toString())
                    fu.write("\n")
                } catch (ex: Exception) {
                    println("writeCats")
                }
            }
            ft.write("\n")
            fu.write("\n")

        }
        ft.close() //закрываем потоки
        fu.close()
    }

    fun writeDelta(deltaA: Double, deltaD: Double, time: Long) {

        try {
            val ft = BufferedWriter(FileWriter(PATH_OUT_FILE_T, true))
            val fu = BufferedWriter(FileWriter(PATH_OUT_FILE_U, true))


            ft.write("$deltaA - deltaA\n")
            ft.write("$deltaD - deltaD\n")

            fu.write("$deltaA - deltaA\n")
            fu.write("$deltaD - deltaD\n")

            fu.write("$time - time in m")
            fu.write("$time - time in m")
            ft.close()
            fu.close()
        } catch (ex: Exception) {
            println("writeDelta")
        }

    }

}
