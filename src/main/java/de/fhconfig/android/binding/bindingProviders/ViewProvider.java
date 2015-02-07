package de.fhconfig.android.binding.bindingProviders;

import android.view.View;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.viewAttributes.view.*;


public class ViewProvider extends BindingProvider {

	@Override
	public ViewAttribute<View, ?> createAttributeForView(View view, String attributeId) {
		if (attributeId.equals("enabled")){
			return new EnabledViewAttribute(view, "enabled");
		}else if (attributeId.equals("visibility")){
			return new VisibilityViewAttribute(view, "visibility");
		}else if (attributeId.equals("background")){
			return new BackgroundViewAttribute(view);
		}else if (attributeId.equals("backgroundColor")){
			return new BackgroundColorViewAttribute(view);
		}else if (attributeId.equals("contextMenu")){
			return new ContextMenuViewAttribute(view);
		}else if (attributeId.equals("onClick")){
			return new OnClickViewEvent(view);
		}else if (attributeId.equals("onLongClick")){
			return new OnLongClickViewEvent(view);
		}else if (attributeId.equals("animation")){
			return new AnimationViewAttribute(view);
		}else if (attributeId.equals("selected")){
			return new SelectedViewAttribute(view);
		}else if (attributeId.equals("onGainFocus")){
			return new OnGainFocusViewEvent(view);
		}else if (attributeId.equals("onLostFocus")){
			return new OnLostFocusViewEvent(view);
		}else if (attributeId.equals("onKey")){
			return new OnKeyViewEvent(view);
		}else if (attributeId.equals("onTouch")){
			return new OnTouchViewEvent(view);
		}else if (attributeId.equals("layout_height")){
			return new Layout_HeightViewAttribute(view);
		}else if (attributeId.equals("layout_width")){
			return new Layout_WidthViewAttribute(view);
		}else if (attributeId.equals("onBind")){
			return new OnBindViewAttribute(view);
		}else if (attributeId.equals("assign")){
			return new AssignViewAttribute(view);
		}else if (attributeId.equals("disallowIntercept")){
			return new DisallowInterceptAttribute(view);
		} else if (attributeId.equals("layout_gravity")) {
			return new Layout_GravityViewAttribute(view);
		} else if (attributeId.equals("gravity")) {
			return new GravityViewAttribute(view);
		}
		return null;
	}
}
