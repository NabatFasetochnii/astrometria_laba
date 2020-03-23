import Core.Companion.PATH_TYCO2
import Core.Companion.dA
import Core.Companion.dA2
import Core.Companion.dD
import Core.Companion.dD2
import Core.Companion.starsSize

//Я написал эту лабу на языке Kotlin, который наследуется от java и также как и java преобразует написанный код
//в байт код, а значит поддерживает кроссплатформенность. Я преследывал цель научиться обращаться с этим языком,
//чтобы улучшить свои навыки в программировании в принципе (котлин объединяет многие фишки разных языков) и
//чтобы было проще заниматься мобильным геймдевом, как известно, в основном котлин используется для этого.
// Я пытался реализовать многопоточность. Благо для данной задачи это оказалось просто.
// Изначально я создавал 3 доп потока для нахождения общих звёзд, потом посмотрел сколько у меня ядер,
//оказалось их шесть, и я добавил еще два потока. Работал я на 6 ядерном процессоре intel Core i7-9750H.
//Многопоточность во второй половине программы не вышла вовсе. Я особо не думал над алгоритмами вычисления
// погрешностей, поэтому не смог качественно использовать все свои ядра. Также, думаю моя реализация ооп
// не слишком хороша, должного разделения функционала нет, нет наследования, например класс Stars можно было
// унаследовать от какого-нибудь List, переопределить некоторые функции, написать итераторы, сделать так,
// чтобы не приходилось плодить списки в таком количестве. Но всё это занимает много времени и не сильно влияет
// на производительность. Хотя я увернен, что не правильно пользовался конструкторами строк, циклами,
// конструкцией when (это вообще какой-то кошмар, программа не должна быть так реализована) и файлами.
// Однако, я делал настолько хорошо, насколько это было мне интересно и думаю, что для своего уровня
// написал не плохо. Этот проект есть на гитхабе по ссылке https://github.com/NabatFasetochnii/astrometria_laba

class Core { //Главный класс, в котором я держу статические объекты.
    // В котлине нет статики в привычном виде, поэтому приходится делать нечто подобное
    //Вроде как такие меры обоснованы и есть разные способы реализовать статику в языке,
    // но я пока не знаю какие тут плюсы и не умею ими пользоваться
    companion object {
        //Путь к каталогу TYCO2, который расположен внутри проекта для удобства
        const val PATH_TYCO2 = "C:\\Users\\Nikita\\IdeaProjects\\astrometria_laba\\src\\tyco2\\DATA\\catalog.dat"
        //Путь к каталогу UCAC2
        const val PATH_UCAC2 = "C:\\Users\\Nikita\\IdeaProjects\\astrometria_laba\\src\\ucac2\\1cd\\u2\\z"
        //Путь к файлу, который содержит данные половны общих пар, которая принадлежит каталогу TYCO2
        const val PATH_OUT_FILE_T = "C:\\Users\\Nikita\\IdeaProjects\\astrometria_laba\\src\\ft.txt"
        //Путь к файлу, который содержит данные половны общих пар, которая принадлежит каталогу UCAC2
        const val PATH_OUT_FILE_U = "C:\\Users\\Nikita\\IdeaProjects\\astrometria_laba\\src\\fu.txt"
        //Счётчик, который я использую для подсчёта пар.Он является статическим т.к.
        // вычисления пар происходит параллельно и у всех потокв должен быть общий счётчик
        var pairs = 0
        //Изменяемый список изменяемых спиков, которые содержат элементы типа Double.
        //Эти списки хранят 288 списков с погрешностями по альфа и дельта.
        // В каждом списке информация для звёзд разделённых
        // по 0.5 градусов по сколнению. Статичность этих объектов также объясняется параллельностью
        // и удобством, но я слабо реализовал параллельные вычисления на этапе подсчёта погрешностей
        var dA: MutableList<MutableList<Double>> = MutableList(288) { MutableList(0) { 0.0 } }
        var dD: MutableList<MutableList<Double>> = MutableList(288) { MutableList(0) { 0.0 } }
        var dA2: MutableList<MutableList<Double>> = MutableList(288) { MutableList(0) { 0.0 } }
        var dD2: MutableList<MutableList<Double>> = MutableList(288) { MutableList(0) { 0.0 } }
        //Колчество звёзд не помню где и зачем
        var starsSize: Int = 0
    }
}

fun main() {//Вход в программу

    var runTime = System.nanoTime() //Фиксируем время, чтобы иметь оценку скорости программы

    val tStars = Stars() // Создаём объект класса Stars для каталога TYCO2
    val uStars = Stars() // Создаём объект класса Stars для каталога UCAC2
    val readAndWrite = ReadCats() // Объект класса, предназначенного для чтения и записи файлов
    val pc = PairCalculation() // объект класса, предназначенного для нахождения общих звёзд. По сути
    // не самый нужный класс, так как этот процесс происходит во время считывания каталога UCAC2 и находится
    // в соответствующем классе, но изначально планировалось для достижения большей скорости загрузить все данные
    // в массив и обрабатывать их в PairCalculation. Такой подход выдавал мне ошибку java heap space
    // и мне пришлось переписать код. Ещё есть вариант засунуть проверку в отдельную функцию в нужном классе,
    // но выглядеть это будет не очень - перекрёстные обращения в двух объектах классов и непонятно ради чего.
    val deltaCalc = DeltaCalc() // объект класса, где мы вычисляем дельты

    // Все классы выше могли бы быть статическими, но, во-первых, в процессе отладки мне приходилось создавать
    // несколько объектов нектороых классов и переписывать основанный на этом код мне не хочется, во-вторых,
    // как я уже говорил, я плохо знаком с заменителями статики в kotlin и решил,
    // что в данном случе это не так необходимо

    readAndWrite.readTYCO(PATH_TYCO2, tStars) // Запускаем считывание каталога TYCO2. Процесс занимает
    // не более нескольких секунд

    systematize(tStars) // Систематизируем полученные данные по склонениям с шагом 0.5 градусов,
    // чтобы было проще работать с файлами каталога UCAC2

    for (i in 0 until 288) { //Заполняем списки каталога UCAC2
        uStars.deSys[i] = MutableList(tStars.deSys[i].size) { 0 } //берём итый список и заполняем его нулями
        // в количестве размера итого систематизированного списка в каталоге TYCO2
        uStars.raSys[i] = MutableList(tStars.raSys[i].size) { 0 }
        uStars.magVSys[i] = MutableList(tStars.magVSys[i].size) { 0f }
        dA[i] = MutableList(tStars.deSys[i].size) { 0.0 }
        dD[i] = MutableList(tStars.deSys[i].size) { 0.0 }
    }

    val runnable1 = Runnable { //Используем несколько потоков, чтобы найти пары общих звёзд

        for (i in 0 until 288 step 6) { //Для каждой итой зоны с шагом 6 (т.к. 6 потоков)
            pc.cal(i, tStars, uStars, readAndWrite) //Передаём номер зоны, внутри которой мы ищем общие звёзды,
            // два каталога, объект класса для чтения и записи
        }
    }
    val runnable2 = Runnable {

        for (i in 1 until 288 step 6) {
            pc.cal(i, tStars, uStars, readAndWrite)
        }
    }
    val runnable3 = Runnable {

        for (i in 2 until 288 step 6) {
            pc.cal(i, tStars, uStars, readAndWrite)
        }
    }
    val runnable4 = Runnable {

        for (i in 3 until 288 step 6) {
            pc.cal(i, tStars, uStars, readAndWrite)
        }
    }
    val runnable5 = Runnable {

        for (i in 4 until 288 step 6) {
            pc.cal(i, tStars, uStars, readAndWrite)
        }
    }
    val thread1 = Thread(runnable1) //Создаём оюъекты класса потока
    val thread2 = Thread(runnable2)
    val thread3 = Thread(runnable3)
    val thread4 = Thread(runnable4)
    val thread5 = Thread(runnable5)
    thread1.start() //Запускаем потоки
    thread2.start()
    thread3.start()
    thread4.start()
    thread5.start()
    for (i in 5 until 288 step 6) { //Даём часть работы основному потоку
        pc.cal(i, tStars, uStars, readAndWrite)
    }

    thread1.join()//ждём завершения всех потоков
    thread2.join()
    thread3.join()
    thread4.join()
    thread5.join()

    var runTime1 = (System.nanoTime() - runTime) / 1_000_000L //смотрим сколько времени это заняло
    println("$runTime1 - time pairs calc in ms")
    runTime1 /= 60_000L
    println("$runTime1 - time pairs calc in m")

    for (i in 0 until  288){
        for (j in 0 until uStars.raSys[i].size){
            if (uStars.raSys[i][j]!=0){
                uStars.raSys2[i].add(uStars.raSys[i][j])
                uStars.deSys2[i].add(uStars.deSys[i][j])
                uStars.magVSys2[i].add(uStars.magVSys[i][j])

                tStars.raSys2[i].add(tStars.raSys[i][j])
                tStars.deSys2[i].add(tStars.deSys[i][j])
                tStars.magVSys2[i].add(tStars.magVSys[i][j])

                dA2[i].add(dA[i][j])
                dD2[i].add(dD[i][j])
            }
        }
    }

    readAndWrite.writeCats(uStars, tStars) //Записываем в файлы данные обоих каталогов

    val runnableRA = Runnable { //Считаем большую систематическую ошибку в двух потоках
        deltaCalc.bigSysDelta(dA2)
    }

    val threadRa = Thread(runnableRA)
    threadRa.start()

    deltaCalc.bigSysDelta(dD2)
//    deltaCalc.arrayFill(uStars)

    threadRa.join() //ждём завершения потока

    deltaCalc.deltaGridA(uStars)
    deltaCalc.deltaGridD()

    deltaCalc.deltaMag(uStars)

    val d = deltaCalc.finalDeltaCalc()

    print(d[0])
    println(" - dA in mas")
    print(d[1])
    println(" - dD in mas")

    runTime -= System.nanoTime()
    runTime /= 1_000_000L
    println("$runTime - time ms")
    runTime /= 60_000L
    println("$runTime - time m")

    readAndWrite.writeDelta(d[0], d[1], runTime)
}

fun systematize(tStars: Stars) {

    var a: Int
    var b: Int
    var c: Float

    val zeroToEc = 90 * 3600_000.0 //Чтобы разбить звёзды TYCO2 на 288 списков,
    // я добавляю к каждому склонению 90 градусов в миллисекундах,
    val halfDegToMas = 0.5 * 3600_000.0 //затем делю каждое склонение на 0.5 градусов в миллисекундах,
    // целая часть от такого деления будет являться номером списка, в который следует записать исходные данные

    starsSize = tStars.dE.size //размер исходных данных

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