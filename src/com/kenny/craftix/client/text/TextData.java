package com.kenny.craftix.client.text;

import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.world.SaveSlotLoader;

public class TextData extends TextLanguage
{
	public static void loadPreTextData()
	{
		TextLoader.addText(tg1); TextLoader.addText(tg5); TextLoader.addText(tg14); TextLoader.addText(tg18);
		
		TextLoader.addText(t1); TextLoader.addText(t2); TextLoader.addText(t3);TextLoader.addText(t4);
		TextLoader.addText(t5); TextLoader.addText(t6); TextLoader.addText(t7); TextLoader.addText(t8);
		TextLoader.addText(t9); TextLoader.addText(t10); TextLoader.addText(t11); TextLoader.addText(t127);
		
		if(InGameSettings.fpsTabOnMenuIn)
		{TextLoader.addText(t12);}
		
		TextLoader.addText(t13); TextLoader.addText(t14); TextLoader.addText(t15);TextLoader.addText(t16);
		TextLoader.addText(t17); TextLoader.addText(t18); TextLoader.addText(t19); TextLoader.addText(t20);
		TextLoader.addText(t19b); 

		TextLoader.addText(t21); TextLoader.addText(t22); TextLoader.addText(t23); TextLoader.addText(t24);
		TextLoader.addText(t25);
		
		TextLoader.addText(t26); TextLoader.addText(t27);
		
		TextLoader.addText(t28); TextLoader.addText(t29); TextLoader.addText(t30); TextLoader.addText(t31);
		TextLoader.addText(t32);
		
		TextLoader.addText(t33); TextLoader.addText(t34); TextLoader.addText(t35);
		
		TextLoader.addText(t36);
		
		TextLoader.addText(t37); TextLoader.addText(t38); TextLoader.addText(t39);TextLoader.addText(t40);
		TextLoader.addText(t41); TextLoader.addText(t42); TextLoader.addText(t43); TextLoader.addText(t44);
		TextLoader.addText(t45);
		
		TextLoader.addText(t46); TextLoader.addText(t47); TextLoader.addText(t48);TextLoader.addText(t49);
		TextLoader.addText(t50); TextLoader.addText(t51); TextLoader.addText(t52); TextLoader.addText(t53);
		TextLoader.addText(t54); TextLoader.addText(t55); TextLoader.addText(t56); TextLoader.addText(t57);
		TextLoader.addText(t128); TextLoader.addText(t129); TextLoader.addText(t130); TextLoader.addText(t57b);
		TextLoader.addText(t131);
		
		TextLoader.addText(t58); TextLoader.addText(t59); TextLoader.addText(t60);TextLoader.addText(t61);
		TextLoader.addText(t62); TextLoader.addText(t63); TextLoader.addText(t64); TextLoader.addText(t65);
		TextLoader.addText(t66); TextLoader.addText(t67);
		
		TextLoader.addText(t68); TextLoader.addText(t69); TextLoader.addText(t70); TextLoader.addText(t71);
		
		TextLoader.addText(t72); TextLoader.addText(t73); TextLoader.addText(t74);
		
		TextLoader.addText(t75); TextLoader.addText(t76); TextLoader.addText(t77); TextLoader.addText(t78);
		TextLoader.addText(t79); TextLoader.addText(t80); TextLoader.addText(t81);TextLoader.addText(t82);
		TextLoader.addText(t83); TextLoader.addText(t84); TextLoader.addText(t85);TextLoader.addText(t86);
		
		TextLoader.addText(t87); TextLoader.addText(t88); TextLoader.addText(t89); TextLoader.addText(t90);
	
		TextLoader.addText(t91); TextLoader.addText(t92); TextLoader.addText(t93); TextLoader.addText(t94);
		TextLoader.addText(t95); TextLoader.addText(t96); TextLoader.addText(t97);
		
		TextLoader.addText(t98);
		
		TextLoader.addText(t99);
		
		TextLoader.addText(t100); TextLoader.addText(t101); TextLoader.addText(t102); TextLoader.addText(t103);
		TextLoader.addText(t104); TextLoader.addText(t105);
		
		TextLoader.addText(t106); TextLoader.addText(t107); TextLoader.addText(t108); TextLoader.addText(t109);
		TextLoader.addText(t109b);
		
		TextLoader.addText(t110); TextLoader.addText(t111); TextLoader.addText(t112); TextLoader.addText(t113);
		TextLoader.addText(t114); TextLoader.addText(t115); TextLoader.addText(t116); TextLoader.addText(t117);
		
		TextLoader.addText(t118); TextLoader.addText(t119); TextLoader.addText(t120); TextLoader.addText(t121);
		TextLoader.addText(t122); TextLoader.addText(t123); TextLoader.addText(t124); TextLoader.addText(t120b);
		TextLoader.addText(t125);
		
		TextLoader.addText(t126);
		
		TextLoader.addText(tt1); TextLoader.addText(tt2); TextLoader.addText(tt3); TextLoader.addText(tt4);
		TextLoader.addText(tt5);
	}
	
	public static void loadPreTextDataSaveSlots()
	{
		TextLoader.addText(SaveSlotLoader.t1_n);
		TextLoader.addText(SaveSlotLoader.t1_f);
		TextLoader.addText(SaveSlotLoader.t1_m);
	}
}
