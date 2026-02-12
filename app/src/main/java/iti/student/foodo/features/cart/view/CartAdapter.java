package iti.student.foodo.features.cart.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import iti.student.foodo.data.db.entity.CartIngredientEntity;
import iti.student.foodo.databinding.CartItemBinding;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    CartItemBinding binding;
    final DeleteItemListener listener;
    List<CartIngredientEntity> list = List.of();

    public CartAdapter(DeleteItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void submitList(List<CartIngredientEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        CartItemBinding binding;

        public ViewHolder(@NonNull CartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CartIngredientEntity item) {
            binding.itemName.setText(item.ingredientName);
            binding.itemMeasure.setText(item.measure);
            binding.deleteItem.setOnClickListener(v -> {
                listener.onDelete(item);
            });
            Glide.with(binding.getRoot()).load(item.imageUrl).thumbnail(0.01f).into(binding.itemImage);
        }
    }

    public interface DeleteItemListener {
        void onDelete(CartIngredientEntity item);
    }
}
