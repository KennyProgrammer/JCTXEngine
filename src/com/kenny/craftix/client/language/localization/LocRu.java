package com.kenny.craftix.client.language.localization;

import static com.kenny.craftix.client.language.Language.*;

import com.kenny.craftix.client.ClientGetterInformation;
import com.kenny.craftix.server.ServerGetterInformation;

public class LocRu 
{
	protected ClientGetterInformation clientInfo;
	protected ServerGetterInformation serverInfo;
	
	public void loadRuLocalization(ClientGetterInformation clientGetterInformation, ServerGetterInformation serverGetterInformation)
	{
		this.clientInfo = clientGetterInformation;
		this.serverInfo = serverGetterInformation;
		
		CLIENT_VERSION = "Версия" + this.clientInfo.getClientVersion();
		SERVER_VERSION = "Версия Cервера" + this.serverInfo.getServerVersion();
		PROFILE_INFO_ENIGNE = "Название: " + this.clientInfo.getClientName();
		PROFILE_INFO_VERSION = CLIENT_VERSION;
		PROFILE_INFO_VERSION_TYPE = "Тип Версии";
		PROFILE_INFO_USERNAME = "Никнейм";
		PROFILE_INFO_USER_ID = "Пользователя Id";
		PROFILE_INFO_SESSION_ID = "Сессии Id";
		PROFILE_INFO_HAS_LICENSE = "Лицензия";
		URI_TITLE = "Переход по Ссылке: ";
		CONTINUE = "Продолжить";
		COMPANY = "Кенни Компания@";
		MAINMENU_SINGLEPLAYER_TITLE = "Одиночная Игра";
		MAINMENU_MULTIPLAYER_TITLE = "Сетевая Игра";
		MAINMENU_OPTIONS_TITLE = "Настройки";
		MAINMENU_CREDITS_TITLE = "Авторы";
		MAINMENU_NEWS_TITLE = "Новости";
		PROFILE_TITLE = "Профиль";
		PROFILE_YOUR_PLAYER = "Твой Игрок";
		MAINMENU_WEBSITE_TITLE = "Группа ВК";
		MAINMENU_EDITOR = "Редактор";
		MAINMENU_HAVENT_LICENSE = "Ты не сможешь играть, потому что не имеешь лицензии! Нажми Esc чтобы вернуться.";
		GUI_BACK = "Назад";
		GUI_ALL = "Всё";
		GUI_CANCEL = "Отмена";
		GUI_CONTINUE = "Продолжить";
		GUI_DONW = "Вниз";
		GUI_NONE = "Ничто";
		GUI_OFF = "Выкл";
		GUI_ON = "Вкл";
		GUI_RETURN = "Вернуться";
		GUI_UP = "Вверх";
		GUI_SMALL = "Низкий";
		GUI_NORMAL = "Нормальный";
		GUI_HIGH = "Высокий";
		GUI_SAVE = "Сохранить";
		GUI_MIN = "Мин";
		GUI_MAX = "Макс";
		LATEST_UPDATES_TITLE = "Обновления";
		NEWS_VERSION_14 = "Лог Версий: 0.0.12 - 0.0.15b (Кратко)";
		NEWS_LOG_DESC_14 = 
				"- Добавлен файл конфигурации. Файл находиться в '.craftix'      " + 
				"- Добавлена локализация в игру, есть русский и английский.      " +
				"- Добавлено больше вкладок в опции такие как: 'Звук', 'Графика'," + 
				"'Язык', и так-же новые настройки.                                      " + 
				"- Теперь все настройки которые вы выберите сохраняться.         " + 
				"- Исправлен баг: при нажатии на крестик игра кряшилась.           " +
				"- Из игрвой сцены теперь можно попасть в меню движка.           " +
				"- В игре с помошью кнопок можно переключать камеры игрока.                         " +
				"                                                                                   " +
				"- Добавлена система аудио (звука) в игру. Звук кнопки добавлен," +
				"музыка в главном меню и в игрвой сцене.";
		NEWS_VERSION_17 = "Лог Версий: 0.0.17";
		NEWS_LOG_DESC_17 = "Работаем...";
		ENGINE_INFO_TITLE =  "Информация об движке Craftix";
		FILE_CONFIG_ERROR = "Файл конфигурации не загружен!";
		OPTION_FULLSCREEN = "Полный Экран";
		OPTION_POST_EFFECTS = "Пост Эффекты";
		OPTION_POST_EFFECTS_MORE = "Дополнительные Пост Эффекты";
		OPTION_POST_EFFECTS_MORE_CONTRAST = "Контраст";
		OPTION_POST_EFFECTS_MORE_BRIGHT_FILTER = "Яркий Свет";
		OPTION_POST_EFFECTS_MORE_GAUSSIAN_BLUR = "Размытие";
		OPTION_GUI_SCALE = "Размер Gui";
		OPTION_FOG = "Туман";
		OPTION_RENDER_DISTANCE = "Прорисовка Мира";
		OPTION_RENDER_SKYBOX = "Рендер Неба";
		OPTION_RENDER_WATER = "Рендер Воды";
		OPTION_SHADOWS = "Тени";
		OPTION_MAX_FPS = "Лимит FPS";
		OPTION_VSYNC = "VSync";
		OPTION_PARTICLES = "Партикли";
		OPTION_BRIGHTNESS = "Яркость";
		FBO = "Фрейм Буффер";
		TRIANGLE_MODE = "Треугольный Мод";
		SOON_AUDIO = "Скоро Настройки Аудио!";
		CREDITS_DESC = "Этот игровой движок был создан Кении(Данил Духовенко) и Компанией Кенни@. Данный движок находится на очень ранней стадии разработки, поэтому ждать от него чего-то сверхъестественного я думаю не стоит. Если кто-то захочет помочь проекту, я буду очень признателен. И все они появиться на этой странице.";
		MULTIPLAYER_CREATE_SERVER = "Создать Cервер";
		MULTIPLAYER_CONNECT_TO_SERVER = "Подключиться к Cерверу";
		CONNECTING_TITLE = "Подключение";
		CONNECTING_CHECK_IP = "Проверка Ip-адреса";
		CONNECTING_SERVER_WAIT_CLIENT = "Ожидание Клиента";
		CONNECTING_SERVER_CLIENT_CONNECT = "Клиент Подключился";
		CONNECTING_CLIENT_JOIN_SERVER = "Заход на Сервер";
		CONNECTING_CLIENT_JOINED = "Успешно Зашел";
		DISCONNECTING_TITLE = "Отсоединение";
		DISCONNECTING_BACK_TO_MENU = "Вернуться";
		DISCONNECTING_QUIT = "Выйти";
		DISCONNECTING_CLINET_UN_IP = "Вы не можете подключиться к серверу, так как ваш IP-адрес недоступен";
		DISCONNECTING_SERVER_UN_IP = "Вы не можете создать сервер, потому что ваш IP-адрес недоступен.";
		LOAD_WORLD = "Загрузить Мир";
		GEN_WORLD = "Создать Мир";
		GEN_LP_WORLD = "Создать Лоу-Поли Мир";
		GEN_F_WORLD = "Создать Плоский Мир";
		GEN_E_WORLD = "Создать Пустой Мир";
		CREATE_WORLD_TITLE = "Создать Мир";
		CREATE_WORLD_CREATE = "Создать";
		CREATE_WORLD_WORLD_NAME = "Имя Мира";
		CREATE_WORLD_SAVE_AS = "Сохранить в";
		CREATE_WORLD_GAMEMODE = "Режим Игры";
		CREATE_WORLD_WORLD_TYPE = "Тип Мира";
		CREATE_WORLD_WORLD_SEED = "Сид Мира";
		CREATE_WORLD_OTHER_SETTINGS = "Другие Конфигурации";
		MAINMENU_QUIT_TITLE = "Выйти из Игры";
		QUIT_GAME = "Выйти из Игры";
		GRAPHICS_TITLE = "Графика";
		AUDIO_TITLE = "Аудио/Звук";
		LANGUAGE_TITLE = "Язык";
		OTHER_TITLE = "Остальные Настройки";
		OPENGL_SETTINGS_TITLE = "Open Gl Настройки";
		FOV = "Fov: ";
		LANG_DESC = "Выберите нужный вам язык:";
		LANG_EN = "Английский";
		LANG_RU = "Русский";
		OPTION_CHANGE_DESC = "Чтоб все изменения сохранились, перезагрузите игру.";
		RESOLUTION = "Разрешение Экрана:";
		SCREEN_1600x900 = "1600x900";
		SCREEN_1280x720 = "1280x720";
		SCREEN_1200x600 = "1200x600";
		SCREEN_640x540 = "640x540";
		AUDIO_ON_OFF = "Вкл/Выкл Звук: ";
		QUIT_GAME_DESC = "Вы действительно хотите выйти из игры?";
		RETURN = "Вернуться";
		RESPAWN = "Возрадиться";
		OTHER_TITLE = "Остальные Настройки";
		OTHER_FPS_TAB = "Fps в Меню";
		OTHER_RENDER_CURSOR = "Рендер Курсора";
		CONTROLS_TITLE = "Управление";
		CONTROLS_CONTROL_FORWARD = "Идти Вперёд";
		CONTROLS_CONTROL_BACKWARD = "Идти Назад";
		CONTROLS_CONTROL_LEFTWARD = "Идти Налево";
		CONTROLS_CONTROL_RIGHTWARD = "Идти Направо";
		CONTROLS_CONTROL_JUMP = "Прыжок";
		CONTROLS_CONTROL_RUN = "Бег";
		CONTROLS_CONTROL_INVENTORY = "Инвентарь";
		CONTROLS_CONTROL_PICKUP = "Подобрать";
		CONTROLS_CONTROL_PAUSE = "Пауза";
		CONTROLS_CONTROL_PLAYERTAB = "Табл Игроков";
		CONTROLS_CONTROL_INFOPAGE = "Информация";
		CONTROLS_SELECT_CONTROL_DESC = "Нажмите на ту клавишу которую хотите оставить и нажмите Сохранить.";
		GL_SETTINGS_DESC = "Эта информация необходима нам и вам, чтобы было проще определить ошибку или баг связынный с Open GL";
		GL_SETTINGS_ARB_VBO = "Arb буффер вершин";
		GL_SETTINGS_VBO = "Буффер Вершин";
		GL_SETTINGS_MULTITEXTURE = "Мультитекстурирование";
		GL_SETTINGS_TEXTURE_COMBINE = "Текстура Комбинации Arv";
		GL_SETTINGS_SHADERS = "Шейдеры";
		GL_SETTINGS_FRAMEBUFFER = "Буффер фреймов";
		GL_SETTINGS_GL14 = "Версия GL 14";
		GL_SETTINGS_GL21 = "Версия GL 21";
		GL_ERROR_SHADER_NO_SHADERS = "Не поддерживаються шейдеры! Обновите OpenGl или драйвера карты.";
		GL_ERROR_2 = "Не поддерживаеться '400 core' версия шейдеров!";
		GL_ERROR_3 = "Устаревшая версия OpenGL. Необходимо 3.0 +.";
		GL_ERROR_4 = "";
		GL_ERROR_5 = "";
		LOADING_WORLD = "Загрузка Мира";
		LOADING_BUILDING_TERRAIN = "Подготовка Генерации";
		LOADING_GENERATE_TERRIAN = "Генерация Ландшафта";
		LOADING_PROCESSING_ENTITY = "Отрисовка Обьектов";
		LOADING_SAVING_WORLD = "Сохранение Мира";
		GAMEPAUSE_TITLE = "Пауза";
		GAMEPAUSE_RESUME = "Возобновить";
		GAMEPAUSE_OPTIONS = "Настройки";
		GAMEPAUSE_SAVE_WORLD = "Сохранить Мир";
		GAMEPAUSE_HELP = "Помощь";
		GAMEPAUSE_BACK_MENU = "В Главное Меню";
		GAMEPAUSE_SAVE_AND_EXIT = "Выйти";
		GAMEOVER_TITLE = "Игра окончена";
		GAMEOVER_RESPAWN = "Возрадиться";
		GAMEOVER_BACK_TO_MENU = "В меню";
		GAMEOVER_REASON_HEALTH = "Вы умерли из за нехватки здоровья.";
		GAMEOVER_REASON_COMMAND = "Вы были изгнаны из этого мира.";
		GAMEOVER_REASON_WATER = "Вы задохнулись в водяных мучениях.";
		EDITOR_SWITCH = "Вы действительно хотите перейти в бета редактор?";
		
	}
}
