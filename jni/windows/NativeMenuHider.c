#include <windows.h>
#include "NativeMenuHider.h"

void showHideWindow( int showHide )
{
	//  task bar
	HWND tb = FindWindowEx( NULL, NULL, "Shell_TrayWnd", NULL );
	ShowWindow( tb, showHide );
	
	// Pre-Vista start button
	ShowWindow( FindWindowEx( tb, NULL, "Button", NULL ), showHide );
	
	// Vista start button
	ShowWindow(FindWindowEx(NULL, NULL, MAKEINTATOM(0xC017), NULL), showHide);
}

JNIEXPORT void JNICALL Java_NativeMenuHider_hideMenuJNI
  (JNIEnv * env, jobject o)
{
	showHideWindow( SW_HIDE );
}

JNIEXPORT void JNICALL Java_NativeMenuHider_showMenuJNI
  (JNIEnv * env, jobject o)
{
	showHideWindow( SW_SHOW );
}
