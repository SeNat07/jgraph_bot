require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Привет! Я виртуальный ассистент Икра компании Тельняшка. Я могу рассказать про часы работы офиса или помочь оставить отзыв о нашей работе. || htmlEnabled = true, html = "Привет! Я виртуальный ассистент <b>Икра </b>компании <b>Тельняшка</b>. Я могу рассказать про часы работы офиса или помочь оставить отзыв о нашей работе."
        buttons:
            "Часы работы" -> /Часы работы
            "Оставить отзыв" -> /Оставить отзыв
        intent: /Часы работы || onlyThisState = false, toState = "/Часы работы"
        intent: /Оставить отзыв || onlyThisState = false, toState = "/Оставить отзыв"

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

    state: Часы работы || sessionResult = " ", sessionResultColor = "#7524AA"
        a: Наш офис работает пн-пт с 9 до 18, сб с 11-16. Воскресенье выходной || htmlEnabled = false, html = "Наш офис работает пн-пт с 9 до 18, сб с 11-16. Воскресенье выходной"
        go!: /Меню

    state: Оставить отзыв || sessionResult = "Отзыв", sessionResultColor = "#B65A1E"
        a: Мы будем рады улучшить сервис по вашим пожеланиям. Пожалуйста оцените работу || htmlEnabled = false, html = "Мы будем рады улучшить сервис по вашим пожеланиям. Пожалуйста оцените работу"
        buttons:
            "Отличный сервис" -> /Оценка Отлично
            "Кое-что можно улучшить" -> /Средняя оценка
            "Все плохо" -> /Средняя оценка

    state: Оценка Отлично || sessionResult = "Отзыв", sessionResultColor = "#B65A1E"
        a: Спасибо! Рады стараться для вас! || htmlEnabled = false, html = "Спасибо! Рады стараться для вас!"
        go!: /Меню

    state: Меню || sessionResult = " ", sessionResultColor = "#15952F"
        buttons:
            "Часы работы" -> /Часы работы
            "Оставить отзыв" -> /Оставить отзыв
        intent: /Часы работы || onlyThisState = false, toState = "/Часы работы"
        intent: /Оставить отзыв || onlyThisState = false, toState = "/Оставить отзыв"

    state: Отправить отзыв || sessionResult = "Отзыв", sessionResultColor = "#B65A1E"
        Sms: 
            text = Отзыв пользователя: {{$request.query}}
            destination = +79378892921
            providerConfiguration = {}
            okState = /Отзыв отправлен
            errorState = /Оставить отзыв

    state: Отзыв отправлен || sessionResult = "Отзыв", sessionResultColor = "#B65A1E"
        a: Спасибо за ваш отзыв, я отправил его директору || htmlEnabled = false, html = "Спасибо за ваш отзыв, я отправил его директору"
        go!: /Меню