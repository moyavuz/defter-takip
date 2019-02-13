import { IMalzeme } from 'app/shared/model/malzeme.model';
import { IIscilik } from 'app/shared/model/iscilik.model';

export interface IPoz {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    yil?: number;
    fiyatTelekom?: number;
    fiyatGirisim?: number;
    fiyatTaseron?: number;
    fiyatTasere?: number;
    kdvOrani?: number;
    malzemeMi?: boolean;
    aktifMi?: boolean;
    malzeme?: IMalzeme;
    iscilik?: IIscilik;
}

export class Poz implements IPoz {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public yil?: number,
        public fiyatTelekom?: number,
        public fiyatGirisim?: number,
        public fiyatTaseron?: number,
        public fiyatTasere?: number,
        public kdvOrani?: number,
        public malzemeMi?: boolean,
        public aktifMi?: boolean,
        public malzeme?: IMalzeme,
        public iscilik?: IIscilik
    ) {
        this.malzemeMi = this.malzemeMi || false;
        this.aktifMi = this.aktifMi || false;
    }
}
