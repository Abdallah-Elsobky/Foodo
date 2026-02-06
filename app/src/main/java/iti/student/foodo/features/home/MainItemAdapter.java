package iti.student.foodo.features.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.student.foodo.data.model.MainItem;
import iti.student.foodo.databinding.MainListItemBinding;

public class MainItemAdapter extends RecyclerView.Adapter<MainItemAdapter.ViewHolder> {
    MainListItemBinding binding;
    List<MainItem> list = List.of();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = MainListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainItem item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void submitList(List<MainItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    final class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(MainListItemBinding binding) {
            super(binding.getRoot());
        }

        public void bind(MainItem item) {
            binding.name.setText(item.getName());
            binding.icon.setText(item.getImage());
        }
    }
}
